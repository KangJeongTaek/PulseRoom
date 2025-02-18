async function edit(comment){
    try{
        const response = await fetch("/comment/edit", {
            method: "POST",
            headers: {
                "Content-Type": "application/json"
            },
            body: JSON.stringify({ comment: comment })
        });

        const responseData = await response.json();
        console.log(responseData);
        if (!response.ok) {
            alert(`실패\n실패 사유: ${responseData.msg}`);
            return;
        }
        if(response.ok){
            alert(`등록 완료! ${responseData.msg}`);
        }

    } catch (e) {
        console.log(e);
        alert(`오류 발생: ${e}`);
    }
}