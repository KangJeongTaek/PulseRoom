async function edit(comment){
    try{
        const response = await fetch("/comment/edit", {
            method: "POST",
            headers: {
                "Content-Type": "application/json"
            },
            body: JSON.stringify({ comment: comment })
        });

        if (!response.ok) {
            const errorData = await response.json();
            alert(`실패\n실패 사유: ${errorData.message}`);
            return;
        }

        const data = await response.json();

    } catch (e) {
        alert(`오류 발생: ${e.message}`);
    }
}