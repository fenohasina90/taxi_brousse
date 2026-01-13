$(document).ready(function() {
    $(document).on('shown.bs.modal', function (e){
        var $modal = $(e.target);
        var modalId = $modal.attr('id');

        if (modalId && modalId.startsWith('modalLignes')) {
            var $table = $modal.find('table');
            if ($table.length && !$.fn.DataTable.isDataTable($table)) {
                console.log("Modal.js est charge - aucun initialisation datatable necessaire")
                $table.DataTable({
                    "language": {
                        "url": "/app-assets/vendors/js/tables/datatable/French.json"
                    },
                    "searching" : false,
                    "lengthChange" : false,
                    "info" : false,
                    "paging" : false
                    // "order" : [],
                    // "columnDefs" : [
                    //     {"orderable" : false, "targets" : "_all"}
                    // ]
                });
            }
        }

    });

    $(document).on('hidden.bs.modal', function (e){
        var $modal = $(e.target);
        var modalId = $modal.attr('id');

        if (modalId && modalId.startsWith('modalLignes')) {
            var $table = $modal.find('table');

            if ($table.length && $.fn.DataTable.isDataTable($table)) {
                $table.DataTable().destroy();
            }
        }
    });


});