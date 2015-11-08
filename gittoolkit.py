import urllib2
import base64
import json
# BASIC_URL = 'https://crl.ptopenlab.com:8800/thugit/'
BASIC_URL = 'http://218.247.230.201/dispatches/file?'

def GetRepoFile(user_name, exp_id, file_path):
    full_url = BASIC_URL+"anonym_id={}&exp_id={}&file_path={}".format(user_name, exp_id, file_path)
    print '\t Get file from {}'.format(full_url)
    request = urllib2.Request(full_url)
    response = urllib2.urlopen(request).read()
    response = json.loads(response)
    if response['found'] == 'True':
        return True, base64.decodestring(response['content'])
    else:
        return False, response['message']
