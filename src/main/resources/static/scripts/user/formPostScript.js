const submitBtn = document.getElementById("submitBtn");
const form = document.getElementById("myForm");
const charCount = document.getElementById("charCount");

const inputTitle = document.getElementById("inputTitle");
const inputContent = document.getElementById("inputContent");

const httpMethod = document.getElementById("httpMethod");


form.addEventListener("submit", function(event){
	event.preventDefault();
	
	const title = inputTitle.value.trim();
	const content = inputContent.value.trim();
	
	const method = httpMethod.value.toUpperCase();
	
	let valid = true;
	
	if(title === '' && content === ''){
		valid = false;
		alert("Title and Content cannot be empty!");	
	} else {
		if(title === ''){
			valid = false;
			alert("Title cannot be empty!");
		}
		if(content === ''){
			valid = false;
			alert("Content cannot be empty!");
		}
	}
	if(title.length > 50){
		valid = false;
		alert("Title cannot exceed 50 characters!");
	}
	if(content.length > 2000){
		valid = false;
		alert("Content cannot exceed 2000 characters!");
	}
	
    if(valid){
        const postData = {
            title: title,
            content: content
        };

        fetch(form.action, {
            method: method,
            headers: {
                "Content-Type": "application/json"
            },
            body: JSON.stringify(postData)
        }).then(response => {
            if(response.ok){
                window.location.href = "/mainpage";    
            } else {
            	response.text().then(message=>{
            		alert("Failed to submit form.\nError: "+message);
            	});
            }    
        }).catch(error => {
            alert("An error occurred");
        });
    }
});

function updateCharCount() {
    const length = inputContent.value.trim().length;
    charCount.textContent = `${length} characters`;
} 

inputContent.addEventListener("input", updateCharCount);
