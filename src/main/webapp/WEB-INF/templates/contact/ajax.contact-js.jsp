<script type="text/javascript">
    $(document).ready(function () {
        function loadContactData(pageNo) {
            let url = CONTEXT + '/contact-rest';
            $.get(url, {page: pageNo, keyword: $('#keyword').val(), lu_contact_type:'person'}).done(function (data) {
                if(data.rowsCount < 1) {
                    $('#data-table').empty();
                    return;
                }
                $('#data-table').empty();
                var $body = $('#data-table');
                $.each(data.records, function (idx, val) {
                    $body.append('<tr>' +
                        '<td>' + val.oid + '</td>' +
                        '<td>' + val.luTitleTitle + '</td>' +
                        '<td>' + val.name + ' ' +val.family + '</td>' +
                        '<td>' + (val.filename?  '<img ' +
                            'src="/uploads/' + val.filename + '" class="img-thumbnail mw50" ' +
                            'alt="' + val.name + ' ' + val.family + '" ' +
                            'title="' + val.name + ' ' + val.family + '"/>': '') +
                        '</td>' +
                        '<td><input type="radio" data-title="' + val.name + ' ' + val.family +
                        '" value="' + val.oid + '" class="chooser"/></td>' +
                        '</tr>');
                });
                loadPaging(url, data);
            });
        }

        $('.modal').on('show.bs.modal', function (e) {
            let id = $(this).attr('id');
            let $button = $(e.relatedTarget);
            $(this).data('codeholder', $button.data('codeholder'));
            $(this).data('titleholder', $button.data('titleholder'));
            loadContactData();
        });
        $('.ajax-search-form').on('submit', function (e) {
            e.preventDefault();
            loadContactData();
        });
        $(document).on('click', 'li.page-item>a.page-link', function (e) {
            if($(this).attr('href')) {
                let page = $(this).attr('href').substr(1);
                if(!isNaN(page)) loadContactData(page);
            }
        });

        $(document).on('click', 'input[type="radio"].chooser', function (e) {
            let id = $(this).parents('.modal').attr('id');
            $('#' + $('#' + id).data('codeholder')).val($(this).val());
            $('#' + $('#' + id).data('titleholder')).val($(this).data('title'));
        });


    });
</script>