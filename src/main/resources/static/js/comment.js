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

        if (!response.ok) {
            alert(`실패\n실패 사유: ${responseData.message}`);
            return;
        }

    } catch (e) {
        alert(`오류 발생: ${e.message}`);
    }
}