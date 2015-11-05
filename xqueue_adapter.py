import urllib
import urllib2
import json

XQUEUE_BASIC_URL = "http://xqueue.xuetangx.com"

def GetSession(user_name, user_password):
    form = {
        "username":user_name,
        "password":user_password
    }
    data = urllib.urlencode(form)
    request = urllib2.Request(XQUEUE_BASIC_URL+"/xqueue/login/", data)
    response = urllib2.urlopen(request)
    cookie = response.headers.get('Set-Cookie')
    return cookie, response.read()

def WaitingJob(cookie, queue_name):
    form = {
        "queue_name":queue_name
    }
    data = urllib.urlencode(form)
    request = urllib2.Request(XQUEUE_BASIC_URL+"/xqueue/get_queuelen?"+data)
    request.add_header('cookie', cookie)
    r = urllib2.urlopen(request).read()
    print "WAITING_JOB::{}".format(r)
    return json.loads(r)

def GetJob(cookie, queue_name):
    form = {
        "queue_name":queue_name
    }
    data = urllib.urlencode(form)
    request = urllib2.Request(XQUEUE_BASIC_URL+"/xqueue/get_submission?"+data)
    request.add_header('cookie', cookie)
    r = urllib2.urlopen(request).read()
    print "GET_JOB::{}".format(r)
    return json.loads(r)

def ReturnResult(cookie, queue_header, result):
    form = {
        "xqueue_header":queue_header,
        "xqueue_body":json.dumps(result)
    }
    data = urllib.urlencode(form)
    request = urllib2.Request(XQUEUE_BASIC_URL+"/xqueue/put_result/", data)
    request.add_header('cookie', cookie)
    request.get_method = lambda: 'POST'
    r = urllib2.urlopen(request).read()
    print "RETURN_RESULT::{}".format(r)
    return json.loads(r)
