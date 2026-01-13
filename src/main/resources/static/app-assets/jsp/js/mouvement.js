$(document).ready(function() {
    // Fonction pour convertir la date au format JJ/MM/AAAA en objet Date
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
                // "type": "date-dd-MM-yyyy",
                "targets": 3,
                "render": function(data, type, row){
                    if (type === 'sort' || type === 'type'){
                        return parseDateTwo(data);
                    }
                    return data;
                }
            },
            {
                // "type": "date-dd-MM-yyyy",
                "targets": 4,
                "render": function(data, type, row){
                    if (type === 'sort' || type === 'type'){
                        return parseDateTwo(data);
                    }
                    return data;
                }
            },
            {
                "type":"num-fmt",
                "targets": 1,
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

    // Variable pour stocker la fonction de filtrage personnalisée
    var customFilter = null;

    $('#appliquer-filtres').click(function() {
        // Supprimer les filtres personnalisés précédents
        $.fn.dataTable.ext.search = [];
        
        // Récupérer les valeurs des filtres
        var formeId = $('#forme-recipient').val();
        var moisAnnee = $('#mois-annee').val();
        var dateDebut = $('#date-debut').val();
        var dateFin = $('#date-fin').val();
        

        // Appliquer les filtres simples (forme et statut)
        table.column(0).search(formeId || '');

        // Créer un filtre personnalisé pour les dates
        customFilter = function(settings, data, dataIndex) {
            var dateRow = data[3]; // La date est dans la 3ème colonne
            var rowDate = new Date(parseDateTwo(dateRow));
            
            // Filtre par mois/année
            if (moisAnnee) {
                // console.log(moisAnnee);
                
                var ma = new Date(parseDate(moisAnnee+'-01'))
                // var maParts = moisAnnee.split('-');
                var maYear = ma.getFullYear();
                var maMonth = ma.getMonth();
                
                if (rowDate.getFullYear() !== maYear || rowDate.getMonth() !== maMonth) {
                    return false;
                }
            }
            
            // Filtre par plage de dates
            if (dateDebut || dateFin) {
                // console.log(dateDebut);
                
                var startDate = dateDebut ? new Date(parseDate(dateDebut)) : null;
                var endDate = dateFin ? new Date(parseDate(dateFin)) : null;
                
                if (startDate && !endDate) {
                    if (rowDate < startDate) return false;
                } else if (!startDate && endDate) {
                    if (rowDate > endDate) return false; 
                } else if (startDate && endDate) {
                    if (rowDate < startDate || rowDate > endDate) return false;
                }
            }
            
            return true;
        };

        // Ajouter le filtre personnalisé
        $.fn.dataTable.ext.search.push(customFilter);
        
        // Redessiner la table
        table.draw();
    });

    // Réinitialiser les filtres
    $('#reinitialiser-filtres').click(function() {
        $('#forme-recipient').val('');
        $('#date-debut').val('');
        $('#date-fin').val('');
        $('#mois-annee').val('');
        
        // Supprimer tous les filtres personnalisés
        $.fn.dataTable.ext.search = [];
        
        // Réinitialiser les recherches par colonne
        table.columns().search('').draw();
    });
});    