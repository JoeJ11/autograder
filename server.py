import gittoolkit as git
import xqueue_adapter as xqueue
import handler

import json
import time
import thread

# with open('user_config', 'r') as f_in:
#     config = json.loads(f_in)
config = {
    'username':'tsinghua',
    'password':'513WLQL5YIMJO7R8EZB3ZUXXSHBI9H5F',
    'queuename':'Tsinghua-Thu64100033X-studio'
}

cookie, resonse = xqueue.GetSession(config['username'], config['password'])
queue_name = config['queuename']

while(True):
    response = xqueue.WaitingJob(cookie, queue_name)
    if response['content'] > 0:
        job = xqueue.GetJob(cookie, queue_name)
        handler.Handle(job['content'], queue_name, cookie)
    else:
        time.sleep(1)
