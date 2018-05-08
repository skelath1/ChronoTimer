
    $(document).ready(function() {
        jQuery.noConflict();
        var $body = jQuery('tbody');
        var timeArr = [];
        var elems = [];
        var cnt = 0;
        jQuery('#dataTable tbody tr').each(function(){
            var $currentRow = jQuery(this);
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

        timeArr = sorter(timeArr);
        for(var i = 0; i < timeArr.length; i++){
            console.log("time array: " + timeArr[i]);
        }
        var newTable = [];
        var zeroNum = 0;
        for(var i = 0; i < timeArr.length; i++){
            if(timeArr[i]  == 9999999999999){
                var index = cloneArr.indexOf(timeArr[i], zeroNum);
                zeroNum = index+1;
            }else{
                var index = cloneArr.indexOf(timeArr[i]);
            }
            console.log("index: " + index);

            newTable[i] = elems[index];
        }

        jQuery.each(newTable, function(index, row){
            $body.append(row);
        });

        var amountOfRows = jQuery('#dataTable tbody tr').length;
        for(var i = 1; i < amountOfRows + 1; i++) {
            var x = document.getElementById("dataTable").rows[i].cells;
            x[0].innerHTML = i;
        }


        function convertToMilli (time){
            if (time == "DNF"){return Infinity;}
            if(time == "00:00:00.00"){return 9999999999999;}
            var arr = time.split(":");
            var hour = parseInt(arr[0], 10) * 60 *60 *1000;
            var minute =  parseInt(arr[1], 10) * 60 *1000;
            var secAndMilli = arr[2].split(".");
            var seconds =  parseInt(secAndMilli[0], 10) *1000;
            var milli =  parseInt(secAndMilli[1], 10);

            return hour + minute + seconds + milli;
        }
        function sorter(arr){
            var switche = true;
            var i = 0;
            var temp;
            while(switche){
                switche = false;
                i++;
                for(var j = 0; j < arr.length-i; j++){
                    if(arr[j] > arr[j+1]){
                        temp = arr[j];
                        arr[j] = arr[j+1];
                        arr[j+1] = temp;
                        switche = true;
                    }
                }
            }
            return arr;

        }
    });
