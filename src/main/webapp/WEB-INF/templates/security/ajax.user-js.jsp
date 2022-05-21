<script type="text/javascript">
    $(document).ready(function () {
        function loadUserData(pageNo) {
            let url = CONTEXT + '/user-rest';
            $.get(url, {page: pageNo, keyword: $('#keyword').val()}).done(function (data) {
                if(data.rowsCount < 1) {
                    $('#ajax-user-data-table').empty();
                    return;
                }
                $('#ajax-user-data-table').empty();
                var $body = $('#ajax-user-data-table');
                $.each(data.records, function (idx, val) {
                    $body.append('<tr>' +
                        '<td>' + val.oid + '</td>' +
                        '<td>' + val.username + '</td>' +
                        '<td>' + val.fkContactOidTitle + '</td>' +
                        '<td><input type="radio" data-title="' + val.username + ' (' + val.fkContactOidTitle + ')' +
                        '" value="' + val.oid + '" class="chooser"/></td>' +
                        '</tr>');
                });
                loadPaging(url, data);
            });
        }

        $('#userModalScrollable').on('show.bs.modal', function (e) {
            let id = $(this).attr('id');
            let $button = $(e.relatedTarget);
            $(this).data('codeholder', $button.data('codeholder'));
            $(this).data('titleholder', $button.data('titleholder'));
            loadUserData();
        });
        $('.ajax-user-search-form').on('submit', function (e) {
            e.preventDefault();
            loadUserData();
        });
        $(document).on('click', 'li.page-item>a.page-link', function (e) {
            if($(this).attr('href')) {
                let page = $(this).attr('href').substr(1);
                if(!isNaN(page)) loadUserData(page);
            }
        });

        $(document).on('click', 'input[type="radio"].chooser', function (e) {
            let id = $(this).parents('.modal').attr('id');
            $('#' + $('#' + id).data('codeholder')).val($(this).val());
            $('#' + $('#' + id).data('titleholder')).val($(this).data('title'));
        });


    });
</script>