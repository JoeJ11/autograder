import urllib2
import base64
import json
# BASIC_URL = 'https://crl.ptopenlab.com:8800/thugit/'
BASIC_URL = 'https://crl.ptopenlab.com:8800/thumanage/dispatches/file?'

def GetRepoFile(user_name, exp_id, file_path):
    full_url = BASIC_URL+"account_name={}&exp_id={}&file_path={}".format(user_name, exp_id, file_path)
    print full_url
    request = urllib2.Request(full_url)
    response = urllib2.urlopen(request).read()
    response = json.loads(response)
    if response['found'] == 'True':
        return base64.decodestring(response['content'])
    else:
        return False
