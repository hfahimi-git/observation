function digit2persian(str){
    var en = ["0", "1", "2", "3", "4", "5", "6", "7", "8", "9"]
    var fa = ["۰", "۱", "۲", "۳", "۴", "۵", "۶", "۷", "۸", "۹"]
    for(var i = 0; i < en.length; i++)
        str = str.replace(en[i] , fa[i]);
    return str;
}

function objectifyForm(formArray) {//serialize data function

    var returnArray = {};
    for (var i = 0; i < formArray.length; i++){
        returnArray[formArray[i]['name']] = formArray[i]['value'];
    }
    return returnArray;
}

function addCommas(nStr) {
    nStr += '';
    x = nStr.split('.');
    x1 = x[0];
    x2 = x.length > 1 ? '.' + x[1] : '';
    var rgx = /(\d+)(\d{3})/;
    while (rgx.test(x1)) {
        x1 = x1.replace(rgx, '$1' + ',' + '$2');
    }
    return x1 + x2;
}
(function () {

    $('input,textarea').on('keyup', function (e) {
        var persianChars = '۰۱۲۳۴۵۶۷۸۹اآبپتثجچحخدذرزسشصضطظعغکگفقلمنوهی';
        if( persianChars.indexOf($(this).val().substring(0, 1)) >= 0) {
            $(this).css('direction', 'rtl');
        }
        else {
            $(this).css('direction', 'ltr');
        }
    });
    
    
    // feather.replace();

    $('.table-sortable').addSortWidget({
        img_asc: CONTEXT + '/assets/img/asc_sort.gif',
        img_desc: CONTEXT + '/assets/img/desc_sort.gif',
        img_nosort: CONTEXT + '/assets/img/no_sort.gif',
    });

/*
    $('.form-signin').on('submit', function () {
        $('#ps').val($.sha256($.sha256($('inputPassword').val()) + $('#s')));
        $('#inputPassword').val('');
        $(this).submit();
    });
*/
    $(".date-picker-input").datepicker({isRTL: true, dateFormat: "yy/m/d",showButtonPanel: true});
}());
