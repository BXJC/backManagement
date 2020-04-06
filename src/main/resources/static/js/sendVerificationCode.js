var xmlHttpRequest;
function createXMLHttpRequest()
{
    if (window.XMLHttpRequest) //非IE浏览器
    {
        xmlHttpRequest = new XMLHttpRequest();
    }
    else if (window.ActiveObject)//IE6以上版本的IE浏览器
    {
        xmlHttpRequest = new ActiveObject("Msxml2.XMLHTTP");
    }
    else //IE6及以下版本IE浏览器
    {
        xmlHttpRequest = new ActiveObject("Microsoft.XMLHTTP");
    }
}

function sendvcode() {
    var phoneNumber = document.getElementById("phoneNumber").value;

    if(phoneNumber != null)
         sendRequest("/account/sendVCode?phoneNumber=" + phoneNumber);
    else
    {
        var msgInfo = "<p>请输入手机号</p>";

        console.log(msgInfo);
        var div2 = document.getElementById("phoneMsg");
        div2.innerHTML=msgInfo;
    }
}

function sendRequest(url) {
    createXMLHttpRequest();
    xmlHttpRequest.open("GET", url, true);
    xmlHttpRequest.onreadystatechange = processResponse;
    xmlHttpRequest.send(null);
}

function processResponse() {
    if (xmlHttpRequest.readyState == 4) {
        if (xmlHttpRequest.status == 200) {
            var responseInfo = "<p>"+xmlHttpRequest.responseText+"</p>";

            console.log(responseInfo);
            var div1 = document.getElementById("vcodeMsg");
            div1.innerHTML=responseInfo;
        }
    }
}