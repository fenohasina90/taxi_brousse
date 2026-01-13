$(document).ready(function() {
    function parseDate(dateString) {
        if (!dateString) return null;
        return new Date(dateString);
    }

    function parseDateTwo(dateString) {
        if (!dateString) return null;
        var parts = dateString.split('/');
        var date = parts[2]+'-'+parts[1]+'-'+parts[0];
        return parseDate(date);
    }

    // Initialisation de DataTable
    var table = $('#historique-prix').DataTable({
        "language": {
            "url": "/app-assets/vendors/js/tables/datatable/French.json"
        },
        "columnDefs": [
            { 
                "targets": 2,
                "render": function(data, type, row){
                    if (type === 'sort' || type === 'type'){
                        return parseDateTwo(data);
                    }
                    return data;
                }
            },
            {
                "type":"num-fmt",
                "targets": 3,
                "render":function (data, type, row){
                    if (type === 'display'){
                        return parseFloat(data).toLocaleString('fr-FR', {
                            minimumFractionDigits: 2,
                            maximumFractionDigits: 2
                        });
                    }
                    return data;
                }
            }// Pour le tri correct des dates
        ]
    });

    // Recharger la table
    $('#reload-table').click(function() {
        table.ajax.reload();
    });

    // Appliquer les filtres
    $('#appliquer-filtres').click(function() {
        // Filtre par forme récipient
        var formeId = $('#forme-recipient').val();
        if (formeId) {
            table.column(0).search(formeId).draw();
        } else {
            table.column(0).search('').draw();
        }

        // Filtre par statut client
        var statut = $('#statut-client').val();
        if (statut) {
            table.column(4).search(statut).draw();
        } else {
            table.column(4).search('').draw();
        }

        
    });

    // Réinitialiser les filtres
    $('#reinitialiser-filtres').click(function() {
        $('#forme-recipient').val('');
        $('#statut-client').val('');
        table.columns().search('').draw();
    });
});