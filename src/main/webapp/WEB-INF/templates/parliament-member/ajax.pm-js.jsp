<script type="text/javascript">
    $(document).ready(function () {
        function loadPmData(pageNo) {
            let _comma = '<fmt:message key="comma" />';
            let url = CONTEXT + '/parliament-member-rest';
            $.get(url, {page: pageNo, keyword: $('#keyword').val(), lu_period_no: $('#lu_period_no').val()}).done(function (data) {
                if(data.rowsCount < 1) {
                    $('#ajax-pm-data-table').empty();
                    resetPagingInfo();
                   return;
                }
                $('#ajax-pm-data-table').empty();
                var $body = $('#ajax-pm-data-table');
                $.each(data.records, function (idx, val) {
                    $body.append('<tr>' +
                        '<td>' + val.oid + '</td>' +
                        '<td>' + val.family + _comma + ' ' + val.name + '</td>' +
                        '<td><img src="/uploads/' + val.filename + '" class="img-thumbnail mw50" /></td>' +
                        '<td><input type="radio" data-title="' + val.family + _comma + ' ' + val.name +
                        '" value="' + val.oid + '" class="chooser"/></td>' +
                        '</tr>');
                });
                loadPaging(url, data);
            });
        }

        $('#pmModalScrollable').on('show.bs.modal', function (e) {
            let id = $(this).attr('id');
            let $button = $(e.relatedTarget);
            $(this).data('codeholder', $button.data('codeholder'));
            $(this).data('titleholder', $button.data('titleholder'));
            loadPmData();
        });
        $('.ajax-pm-search-form').on('submit', function (e) {
            e.preventDefault();
            loadPmData();
        });
        $(document).on('click', 'li.page-item>a.page-link', function (e) {
            if($(this).attr('href')) {
                let page = $(this).attr('href').substr(1);
                if(!isNaN(page)) loadPmData(page);
            }
        });

        $(document).on('click', 'input[type="radio"].chooser', function (e) {
            let id = $(this).parents('.modal').attr('id');
            $('#' + $('#' + id).data('codeholder')).val($(this).val());
            $('#' + $('#' + id).data('titleholder')).val($(this).data('title'));
        });


    });
</script>