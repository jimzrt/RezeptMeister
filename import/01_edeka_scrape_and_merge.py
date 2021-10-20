import requests
import json
import time
import os.path
import gzip
import glob

os.chdir(".")
page = 1
size = 50
done = 0
while True and not os.path.isfile("merged_edeka.json.gz"):
    headers = {
        'User-Agent': 'Mozilla/5.0 (X11; Linux x86_64; rv:81.0) Gecko/20100101 Firefox/81.0',
        'Accept': 'application/json, text/plain, */*',
        'Accept-Language': 'en-US,en;q=0.5',
        'Connection': 'keep-alive'
    }

    params = (
        ('query', ''),
        ('page', page),
        ('size', size),
    )

    json_file_name = f'edeka_{page:02}.json'
    if os.path.isfile(json_file_name):
        print("use cached json")
        with open(json_file_name) as f:
            response_json = json.load(f)
    else:
        response = requests.get('https://www.edeka.de/rezepte/rezept/suche', headers=headers, params=params)
        response_json = json.loads(response.text)
        with open(json_file_name, 'w') as outfile:
            json.dump(response_json, outfile, indent=2)
        time.sleep(1)

    done += size
    total_count = response_json["totalCount"]
    size = min(size, total_count - done)
    print(f'{done:04}/{total_count}')
    if done >= total_count:
        break
    page += 1

recipes = {"items": []}
file_paths = sorted([json_file for json_file in glob.glob(
    "edeka*.json") if not os.path.basename(json_file).startswith('merged')])
with open(file_paths[0]) as f:
    json_data = json.load(f)

for file_path in file_paths[1:]:
    with open(file_path) as f:
        json_temp_data = json.load(f)
        json_data["recipes"] += json_temp_data["recipes"]

print(f'got {len(json_data["recipes"])} recipes')

with gzip.open('merged_edeka.json.gz', 'wt', encoding="UTF-8") as zipfile:
   json.dump(json_data, zipfile)

for file_path in file_paths:
    os.remove(file_path)

print("done!")