from flask import Flask, request, jsonify # 간단히 플라스크 서버를 만든다
import json
import requests
import base64
import io
from PIL import Image
from openai import OpenAI

app = Flask(__name__)

@app.route("/tospring")
def spring():

    image_list = []
    image_list2 = []

    response = request.args.get('prompt')
    width = request.args.get('width')
    width2 = int(width)
    height = request.args.get('height')
    height2 = int(height)
    print(width)

    replace = response.replace("/", " ")
    print(replace)

    url = "http://127.0.0.1:7860"
    payload = {
        "prompt": replace,
        "steps": 150,
        "batch_size": 5,
        "override_settions": {
            "sd_model_checkpoint": "avocado",
            "CLIP_stop_at_last_layers": 2,
        }
    }
    response = requests.post(url=f'{url}/sdapi/v1/txt2img', json=payload)
    r = response.json()

    for i in r['images']:
        decoded_image = base64.b64decode(i)
        img = Image.open(io.BytesIO(decoded_image))

        # Resize image (example: resizing to 100x100)
        resized_img = img.resize((width2, height2))

        # Convert resized image to bytes
        with io.BytesIO() as output:
            resized_img.save(output, format="png")
            resized_encoded_image = base64.b64encode(output.getvalue()).decode()

        # Append resized and re-encoded image to list
        image_list.append(resized_encoded_image)


    for j in r['info']:
        image_list2.append(j)

    data_list = json.loads(''.join(image_list2))
    seed_value = data_list["all_seeds"]
    print(seed_value)
    combined_list = list(zip(image_list, seed_value))
    # json_data = [{"key": item[0], "value": item[1]} for item in combined_list]
    # json_string = json.dumps(json_data)
    return combined_list




@app.route("/tospring2")
def spring2():
    image_list = []
    image_list2 = []

    response = request.args.get('prompt')
    print(response)
    width = request.args.get('width')
    width2 = int(width)
    height = request.args.get('height')
    height2 = int(height)
    seed = request.args.get('seed')
    seed.replace("[", "")
    seed.replace("]", "")
    seed2 = int(seed)
    print(seed2)
    print(width)

    replace = response.replace("/", " ")
    print(replace)

    url = "http://127.0.0.1:7860"
    payload = {
        "prompt": replace,
        "steps": 150,
        "batch_size": 1,
        "seed": seed2,
        "override_settions": {
            "sd_model_checkpoint": "bae_step_2000.ckpt",
            "CLIP_stop_at_last_layers": 2,
        }
    }
    response2 = requests.post(url=f'{url}/sdapi/v1/txt2img', json=payload)
    r = response2.json()

    for i in r['images']:
        decoded_image = base64.b64decode(i)
        img = Image.open(io.BytesIO(decoded_image))

        # Resize image (example: resizing to 100x100)
        resized_img = img.resize((width2, height2))

        # Convert resized image to bytes
        with io.BytesIO() as output:
            resized_img.save(output, format="png")
            resized_encoded_image = base64.b64encode(output.getvalue()).decode()

        # Append resized and re-encoded image to list
        image_list.append(resized_encoded_image)

    for j in r['info']:
        image_list2.append(j)

    data_list = json.loads(''.join(image_list2))
    seed_value = data_list["all_seeds"]
    print(seed_value)
    combined_list = list(zip(image_list, seed_value))
    # json_data = [{"key": item[0], "value": item[1]} for item in combined_list]
    # json_string = json.dumps(json_data)
    return combined_list


@app.route("/tospring3")
def spring3():

    subject = request.args.get('subject')
    replace = subject.replace("/", " ")

    client = OpenAI(api_key='api_key')

    query = '배너 광고의 문구를 작성할 예정이야. 광고의 주제는 '+ replace + '이며, 이 주제를 기반으로 광고 문구를 한국어로 작성해줘.'
    message = [{'role': 'user', 'content': query}]
    completion = client.chat.completions.create(model='gpt-3.5-turbo', messages=message)
    response_text = completion.choices[0].message.content
    replace_text = response_text.replace('"', '')
    print(replace_text)

    return replace_text






if __name__ == '__main__':
    app.run(debug=False, host="127.0.0.1", port=8000)
