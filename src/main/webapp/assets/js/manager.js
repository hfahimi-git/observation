(function () {
    function fetchWaitingFile() {
        $.ajax({
            url: CONTEXT + "/management/get-waiting-count", success: function (result) {
                if(result.count > 0)
                    $("#waiting-count").html(digit2persian(result.count));
            }
        });
    }
    fetchWaitingFile();
    setInterval(fetchWaitingFile,15000);
}());