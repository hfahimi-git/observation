<spring:eval expression="@environment.getProperty('paging.page-size')" var="pageSize"/>

<div class="row">
    <div class="col-md-2"><small class="record-count mt-2 dib"><fmt:message key="record_count"/> : </small></div>
    <div class="col-md-9">
        <nav aria-label="Page navigation example">
            <ul class="pagination pagination-sm">
            </ul>
        </nav>
    </div>
</div>
<script>
    function resetPagingInfo(){
        $('.record-count').empty();
        $('.pagination-sm').empty();
    }

    function loadPaging(url, data) {
        let qsWithoutPageNo = '${qsWithoutPageNo}';
        let pageSize = '${pageSize}';
        let _previous = '<fmt:message key="previous"/>';
        let _next = '<fmt:message key="next"/>';
        let _record_count = '<fmt:message key="record_count"/>';

        let pageNo = data.pageNo;
        let pagingPrevious = (pageNo-1 > 0? pageNo-1: 0);
        let pagingFirst = (pageNo-4 <= 0? 1: pageNo-4);
        let pagingEnd = (pageNo+4 >= data.pageCount? data.pageCount: pageNo+4);
        let pagingNext = (pageNo+1 <= data.pageCount? pageNo+1: 0);
        let rowsCount = data.rowsCount;

        $('.record-count').empty().text(_record_count + ' : ' + rowsCount);
        $('.pagination-sm').empty();
        if (pagingFirst != pagingEnd || rowsCount < pageSize) {
            $('.pagination-sm').append(
                preparePrev(pageNo, pagingPrevious) +
                prepareSeries(pagingFirst, pagingEnd, pageNo) +
                prepareNext(pagingNext)
            );
        }

        function prepareSeries(pagingFirst, pagingEnd, pageNo) {
            let series = '<!-- start series -->';
            for(let i = pagingFirst; i<= pagingEnd; i++) {
                series += '<li class="page-item';
                if (i === pageNo) {
                    series += 'active" aria-current="page"><a class="page-link">' + i +
                        '<span class="sr-only">(current)</span></a>';
                } else {
                    series += '"><a class="page-link" href="#' + i +
                        (qsWithoutPageNo? '&' + qsWithoutPageNo: '') + '">' + i + '</a>';
                }
                series += '</li>';
            }
            return series;
        }

        function preparePrev(pageNo, pagingPrevious){
            let prev = '';
            if(pagingPrevious >= 1) {
                prev += '<li class="page-item"><a class="page-link" href="#' + (pageNo - 1) +
                    '">' + _previous + '</a></li>'
            }
            else {
                prev += '<li class="page-item"><a class="prevLink page-link">' + _previous + '</a></li>';
            }
            return prev;
        }

        function prepareNext(pagingNext){
            let next = '';
            if(pagingNext > 0) {
                next += '<li class="page-item"><a class="page-link" href="#' + pagingNext +
                    (qsWithoutPageNo? '&' + qsWithoutPageNo: '') + '">' + _next + '</a></li>'
            }
            else {
                next += '<li class="page-item"><a class="page-link">' + _next + '</a></li>'
            }
            return next;
        }

    }
</script>