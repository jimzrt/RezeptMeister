
import json
import gzip
import sys
from PIL import Image, ImageFilter
import requests
from io import BytesIO
import os.path
import time

recipes_without_image_file = 'recipe_without_image.txt'


with gzip.open('merged_edeka.json.gz') as zipfile:
   json_file = json.load(zipfile)

for idx, recipe in enumerate(json_file["recipes"]):
    seoTitle = recipe["seoTitle"]
    filename_small = f'../static/images/recipe/{seoTitle}_small.jpg'
    filename_big = f'../static/images/recipe/{seoTitle}_big.jpg'
    if not os.path.exists(filename_small):
        # 480px width
        picture_small = recipe["media"]["images"]["ratio_1:1"]["url"]["medium"]
        response = requests.get(picture_small)
        if response.status_code == 404:
            with open(recipes_without_image_file, 'a') as f:
                f.write(f'{recipe["seoTitle"]}\n')
                continue
        with open(filename_small, 'wb') as f:
            f.write(response.content)
        time.sleep(1)
    
    if not os.path.exists(filename_big):
        # 768px width
        # if recipe["media"]["images"]["ratio_16:9"]:
        #     picture_big = recipe["media"]["images"]["ratio_16:9"]["url"]["mediumLarge"]
        #     response = requests.get(picture_big)
        #     with open(filename_big, 'wb') as f:
        #         f.write(response.content)
        #     time.sleep(2)
        # else:
            #Open existing image
        image_org = Image.open(filename_small)
        image_big = image_org.resize((768,768))
        image_org = image_org.resize((432,432))
        image_big = image_big.filter(ImageFilter.GaussianBlur(8))
        image_big.paste(image_org, ((int)(768/2-432/2), (int)(768/2-432/2)))
        area = (0, int((768-432)/2), 768, 432+int((768-432)/2))
        image_big = image_big.crop(area)
        image_big.save(filename_big, "JPEG")
    print(f'recipe {idx}/{len(json_file["recipes"])}')
print("done")
    
