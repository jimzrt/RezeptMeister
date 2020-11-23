
import json
import gzip
import sys
from PIL import Image, ImageFilter, ImageFile
import requests
from io import BytesIO
import os.path
import time
ImageFile.LOAD_TRUNCATED_IMAGES = True

recipes_without_image_file = 'recipe_without_image.txt'


with gzip.open('merged_edeka.json.gz') as zipfile:
   json_file = json.load(zipfile)

for idx, recipe in enumerate(json_file["recipes"]):
    seoTitle = recipe["seoTitle"]
    filename_big = f'../src/main/resources/static/images/recipe/{seoTitle}_big.jpg'
    if not os.path.exists(filename_big):

        target_width = 1520
        target_height = 855
        if recipe["media"]["images"]["ratio_16:9"]:
            picture_big = recipe["media"]["images"]["ratio_16:9"]["url"]["huge"]
            #width = 1920
        elif recipe["media"]["images"]["ratio_3:4"]:
            picture_big = recipe["media"]["images"]["ratio_3:4"]["url"]["extraCompact"]
            #width = 1520

        response = requests.get(picture_big)
        if response.status_code == 404:
            with open(recipes_without_image_file, 'a') as f:
                f.write(f'{recipe["seoTitle"]}\n')
                continue
        print(recipe["seoTitle"], picture_big)
        img = Image.open(BytesIO(response.content))
        if img.width != target_width:
            factor = target_width/img.width
            img = img.resize(((int)(img.width*factor), (int)(img.height*factor)), Image.LANCZOS)
        if img.height != target_height:
            area = (0, img.height//2-target_height//2, target_width, img.height//2+target_height//2)
            img = img.crop(area)
        img.save(filename_big, "JPEG", quality=30, optimize=True, progressive=True)
        time.sleep(2)

    print(f'recipe {idx}/{len(json_file["recipes"])}')
print("done")