class RegisterHandler {

    register(userInfo, callback) {

        // userInfo Example 
        //{ username: "jewbgjew", email: "kwgne", psw: "wejggle", age: "22", gender: "Male", phone: "33" }
        //send data
        // return if register is successful otherwise false
        // returns {status: true}
        // or
        // {status: false, err: " gge "}
        console.log(userInfo)
        callback({status: true})
    }
}