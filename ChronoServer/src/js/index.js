$(document).ready(function(){

       var $body = $('tbody');
       var timeArr = [];
       var elems = [];
       var cnt = 0;
        $('#dataTable tbody tr').each(function(){
                var $currentRow = $(this);
                var time = $currentRow.find(':nth-child(4)');
                var rawTime = time.text().trim();
                timeArr[cnt] = convertToMilli(rawTime);
                elems[cnt] = $currentRow;
                ++cnt;
        });

        var cloneArr = [];
        for(var i = 0; i < timeArr.length; i++){
            cloneArr[i] = timeArr[i];
        }

        timeArr.sort(function(a,b){
            return a-b;
        });

        $.each(timeArr,function(){console.log("timeArr: " + this + " typeof: " + typeof this );});
        $.each(cloneArr,function(){console.log("copy: " + this + " typeof: " + typeof this);});
        var newTable = [];
        for(var i = 0; i < timeArr.length; i++){
            var index = cloneArr.indexOf(timeArr[i]);
            console.log("index: " + index);

            newTable[i] = elems[index];
        }

        $.each(newTable, function(index, row){
            $body.append(row);
        });

        var amountOfRows = $('#dataTable tbody tr').length;
        for(var i = 1; i < amountOfRows + 1; i++) {
            var x = document.getElementById("dataTable").rows[i].cells;
            x[0].innerHTML = i;
        }


   function convertToMilli (time){
       console.log(time);
        if (time == "DNF"){return Infinity;}
        if(time == "00:00:00.000"){return 9999999999999;}
        var arr = time.split(":");
        var hour = parseInt(arr[0], 10) * 60 *60 *1000;
        var minute =  parseInt(arr[1], 10) * 60 *1000;
        var secAndMilli = arr[2].split(".");
        var seconds =  parseInt(secAndMilli[0], 10) *1000;
        var milli =  parseInt(secAndMilli[1], 10);

        return hour + minute + seconds + milli;
   }
});