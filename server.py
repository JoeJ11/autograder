import gittoolkit as git
import xqueue_adapter as xqueue
import handler

import json
import time
import thread
import urllib2
import sys
import os
import shutil

# with open('user_config', 'r') as f_in:
#     config = json.loads(f_in)
config = {
    'username':'tsinghua',
    'password':'513WLQL5YIMJO7R8EZB3ZUXXSHBI9H5F',
    'queuename':'Tsinghua-Thu64100033X-studio'
}

queue_name = config['queuename']
counter = 0
while(True):
    cookie, resonse = xqueue.GetSession(config['username'], config['password'])
    try:
        counter += 1
        if counter == 60*60:
            print 'Initialize working directory.'
            os.chdir(handler.WORK_ROOT)
            shutil.rmtree('{}/{}'.format(handler.WORK_ROOT, handler.WORKING_STAGE))
            os.mkdir('{}/{}'.format(handler.WORK_ROOT, handler.WORKING_STAGE))

        response = xqueue.WaitingJob(cookie, queue_name)
        if response['content'] > 0:
            counter = 0
            tem_job = xqueue.GetJob(cookie, queue_name)
            handler.Handle(tem_job['content'], queue_name, cookie)
        else:
            time.sleep(1)
    except urllib2.URLError as err:
        print 'URL_ERROR:: {}'
        print err
        continue
    except:
        print sys.exc_info()[0]
        handler.Error_response(cookie, json.loads(tem_job['content']), '<p>Error! Please check your repo.</p>')
        continue
