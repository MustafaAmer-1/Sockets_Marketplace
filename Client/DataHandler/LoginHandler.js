class LoginHandler {

    login(userInfo, callback) {
        
        console.log(userInfo)
        // userInfo Example
        //   {
        //    "email": "dgbejbg",
        //    "psw": "kwfwnefn"
        //    }
        // return {status: true} if logged in otherwise false
        // or {status: false, err: "somehting"}
        callback({status: true})
    }
}