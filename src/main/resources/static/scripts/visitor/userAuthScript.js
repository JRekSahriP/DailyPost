const submitBtn = document.getElementById("submitBtn");
const form = document.getElementById("myForm");

const inputUsername = document.getElementById("inputUsername");
const inputPassword = document.getElementById("inputPassword");

form.addEventListener("submit", function(event){
	event.preventDefault();
	
	const username = inputUsername.value.trim();
	const password = inputPassword.value.trim();
	
	let valid = true;
	
	if(username.length > 20){
		valid = false;
		alert("Username cannot exceed 20 characters!");	
	}
	if(password.length > 60){
		valid = false;
		alert("Password cannot exceed 60 characters!");	
	}
	
	if(username === '' || password === ''){
		valid = false;
		alert("Invalid Username or Password!");	
	} 
	
    if(valid){
    	
        const postData = {
            username: username,
            password: password
        };

        fetch(form.action, {
            method: "POST",
            headers: {
                "Content-Type": "application/json"
            },
            body: JSON.stringify(postData)
        }).then(response => {
            if(response.ok){
            	 const redirectURL = form.getAttribute("data-success-url");
                 if (redirectURL) {
                     window.location.href = redirectURL;    
                 } 
            } else {
            	response.text().then(message=>{
            		alert(message);
            	});
            }    
        }).catch(error => {
            alert("An error occurred");
        });
    }
});
