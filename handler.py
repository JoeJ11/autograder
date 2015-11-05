import gittoolkit as git
import xqueue_adapter as xqueue

import os
import shutil
import json

WORKING_STAGE = 'development'

def Handle(job, queue_name, cookie):
    print "HANDLE A JOB"
    job = json.loads(job)
    hw_info = json.loads(job['xqueue_body'])
    # hw_info = job['xqueue_body']
    _create_working_dir(str(json.loads(job['xqueue_header'])['submission_id']))
    payload = json.loads(hw_info['grader_payload'])
    # payload = hw_info['grader_payload']
    student_name = payload['student_name']
    exp_id = payload['exp_id']
    file_path = payload['file_path']
    file_content = _get_remote_file(student_name, exp_id, file_path)
    response = _generate_response(job, file_content)
    _response(cookie, job, response)
    _remove_working_dir(str(json.loads(job['xqueue_header'])['submission_id']))

def _create_working_dir(dir_name):
    os.chdir('./{}'.format(WORKING_STAGE))
    if dir_name in os.listdir('.'):
        shutil.rmtree(dir_name)
    os.mkdir(dir_name)
    os.chdir(dir_name)

def _get_remote_file(user_name, exp_id, file_path):
    file_content = git.GetRepoFile(user_name, exp_id, file_path)
    with open('answer', 'w') as f_out:
        f_out.write(file_content)
    return git.GetRepoFile(user_name, exp_id, file_path)

def _generate_response(job, file_content):
    body = {
        'correct':True,
        'score':10,
        'msg':"<p>Sample message</p>"
    }
    print body
    return body

def _response(cookie, job, body):
    xqueue.ReturnResult(cookie, job['xqueue_header'], body)

def _remove_working_dir(dir_name):
    os.chdir('..')
    shutil.rmtree(dir_name)
    os.chdir('..')
