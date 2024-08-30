Array.from(document.getElementsByClassName("deletePostBtn"))
.forEach(button=>{
	button.addEventListener("click",function(){
		let postId = Number(button.id);
		deletePost(postId);
	});
});
Array.from(document.getElementsByClassName("editPostBtn"))
.forEach(button=>{
	button.addEventListener("click",function(){
		let postId = Number(button.id);
		editPost(postId);
	});
});

function deletePost(id){
	fetch("/restPost/"+id, {
		method: "DELETE",
        headers: {"Content-Type": "application/json"},
	}).then(response=>{
		if(response.ok){
            window.location.reload();
        } else {
        	response.text().then(message=>{
        		 alert("Failed to delete the Post.\nError: "+message);
        	});
        }   
	}).catch(error => {
        alert("An error occurred");
    });
}

function editPost(id){
	window.location.href="/post/edit/"+id;
}
