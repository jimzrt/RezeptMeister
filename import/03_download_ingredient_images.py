#!/usr/bin/env python3

from bs4 import BeautifulSoup
import requests
import re
import sys
import os
import http.cookiejar
import json
import urllib.request, urllib.error, urllib.parse
import json
import regex
import unicodedata
import sys
import gzip
import unidecode
import os.path
from PIL import Image
from io import BytesIO
import time
import string

HEADERS={'User-Agent':"Mozilla/5.0 (Windows NT 6.1; WOW64) AppleWebKit/537.36 (KHTML, like Gecko) Chrome/43.0.2357.134 Safari/537.36"}
INGREDIENT_SIZE = 150

NONLATIN = regex.compile("[^\\w-]")
WHITESPACE = regex.compile("[\\s]+")

seen_ingredients = set()


def make_slug(string: str) -> str:
    slug = string.lower().strip()
    slug = unidecode.unidecode(slug)
    slug = WHITESPACE.sub("-", slug)
    slug = unicodedata.normalize("NFD", slug)
    slug = NONLATIN.sub("", slug)
    return slug


def get_image_url(query):
    params = (
        ('q', query),
        ('FORM', 'HDRSC2'),
        ('safesearch', 'off')
    )
    response = requests.get('http://www.bing.com/images/search', headers=HEADERS, params=params)
    response.raise_for_status()
    soup = BeautifulSoup(response.text, 'html.parser')
    image_result_raw = soup.find("a",{"class":"iusc"})
    m = json.loads(image_result_raw["m"])
    #murl, turl = m["murl"],m["turl"]# mobile image, desktop image
    return m["turl"]

def get_image_from_url(url):
    response = requests.get(url, headers=HEADERS)
    response.raise_for_status()
    img = Image.open(BytesIO(response.content))
    width, height = img.size
    ratio = width/height
    if width > height:
        new_height = INGREDIENT_SIZE
        new_width = (int) (INGREDIENT_SIZE*ratio)
    else:
        new_width = INGREDIENT_SIZE
        new_height = (int) (INGREDIENT_SIZE/ratio)
    img = img.resize((new_width, new_height))
    left = (new_width - INGREDIENT_SIZE)/2
    top = (new_height - INGREDIENT_SIZE)/2
    right = (new_width + INGREDIENT_SIZE)/2
    bottom = (new_height + INGREDIENT_SIZE)/2
    img = img.crop((left, top, right, bottom))

    return img


with gzip.open('merged_edeka.json.gz') as zipfile:
   json_file = json.load(zipfile)

for idx, recipe in enumerate(json_file["recipes"]):
    for ingredientGroup in recipe["ingredientGroups"]:
        for ingredient in ingredientGroup["ingredientGroupIngredients"]:
            name = ingredient["ingredient"]
            if name in seen_ingredients:
                continue
            slug = make_slug(name)
            image_file_name = f'../static/images/ingredient/{slug}.jpg'
            if os.path.exists(image_file_name):
                seen_ingredients.add(name)
                continue
            try:
                image_url = get_image_url(name)
            except Exception as e:
                try:
                    new_name = name.split(' ', 1)[0].translate(str.maketrans('', '', string.punctuation))
                    image_url = get_image_url(new_name)
                except Exception as e:
                    print("Error:")
                    print(name, slug, str(e))
                    #sys.exit(0)
            image = get_image_from_url(image_url)
            image.save(image_file_name, "JPEG")
            seen_ingredients.add(name)
            #time.sleep(3)
    print(f'recipe {idx+1}/{len(json_file["recipes"])}')
print("done")

            


