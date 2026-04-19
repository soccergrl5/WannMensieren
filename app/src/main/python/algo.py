import json
import boto3
from langchain_aws.chat_models import ChatBedrock

brt = boto3.client(service_name="bedrock-runtime",
                   region_name="eu-central-1")

chat_model = ChatBedrock(
        model_id=model_id,
        client=brt,
        model_kwargs={"temperature": 0, "top_k": 1},
    )

response_text = chat_model.invoke("Here is a json file with a list of selected courses. Please suggest me a list of timetables in a json format, where it would be guaranteed, that I have a minimum one hour break in the time span from 11:00 to 14:00 every weekday.").content
print("Model Response:", response_text)