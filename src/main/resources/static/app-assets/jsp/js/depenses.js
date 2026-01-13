$(document).ready(function() {

        function parseDateOne(dateString) {
            if (!dateString) return null;
            return new Date(dateString);
        }

        function parseDateTwo(dateString) {
            if (!dateString) return null;
            var parts = dateString.split('/');
            var date = parts[2]+'-'+parts[1]+'-'+parts[0];
            return parseDateOne(date);
        }

        // fonction pour calculer le total des montants
        function calculateTotal(){
            var total = 0;
            var visibleRows = $('#historique-depense tbody tr:visible');
            visibleRows.each(function () {
                var montantText = $(this).find('.prix').text().trim();
                var montant = parseFloat($(this).find('.prix').data('montant')) || 0;
                total += montant;
            })

            var totalFormate = total.toFixed(2).replace(/\B(?=(\d{3})+(?!\d))/g, ' ') + ' Ar';
            $('#total-prix').text(totalFormate);
            $('#total-header').text(totalFormate)

        }

        

        // Initialisation de DataTable
        var table = $('#historique-depense').DataTable();
        if (!table) {

            // Fonction pour convertir la date au format JJ/MM/AAAA en objet Date
            

            table = $('#historique-depense').DataTable({

                "language": {
                    "url": "/app-assets/vendors/js/tables/datatable/French.json"
                },
                "columnDefs": [
                    
                    // {
                    //     "type":"num-fmt",
                    //     "targets": 1,
                    //     "render":function (data, type, row){
                    //         if (type === 'display'){
                    //             return parseFloat(data).toLocaleString('fr-FR', {
                    //                 minimumFractionDigits: 2,
                    //                 maximumFractionDigits: 2
                    //             });
                    //         }
                    //         return data;
                    //     }
                    // },
                    {
                        "targets": 2,
                        "render": function(data, type, row){
                            if (type === 'sort' || type === 'type'){
                                return parseDateTwo(data);
                            }
                            return data;
                        }
                    }
                ],
                "drawCallback": function (settings){
                    // Calculer le total apres chaque dessin de la table
                    calculateTotal();
                },
                "initComplete": function () {
                    calculateTotal();
                }
            });
        }

        table.on('draw', function () {
            calculateTotal();
        });

        calculateTotal();
        // Variable pour stocker la fonction de filtrage personnalisée
        var customFilter = null;

        $('#appliquer-filtres').click(function() {
            // Supprimer les filtres personnalisés précédents
            $.fn.dataTable.ext.search = [];

            // Récupérer les valeurs des filtres
            var moisAnnee = $('#mois-annee').val();
            var dateDebut = $('#date-debut').val();
            var dateFin = $('#date-fin').val();


            // Créer un filtre personnalisé pour les dates
            customFilter = function(settings, data, dataIndex) {
                var dateRow = data[2]; // La date est dans la 3ème colonne                
                var rowDate = new Date(parseDateTwo(dateRow));
                // Filtre par mois/année
                if (moisAnnee) {
                    var ma = new Date(parseDateOne(moisAnnee+'-01'))
                    // var maParts = moisAnnee.split('-');
                    var maYear = ma.getFullYear();
                    var maMonth = ma.getMonth();

                    if (rowDate.getFullYear() !== maYear || rowDate.getMonth() !== maMonth) {
                        return false;
                    }
                }

                // Filtre par plage de dates
                if (dateDebut || dateFin) {
                    var startDate = dateDebut ? new Date(parseDateOne(dateDebut)) : null;
                    var endDate = dateFin ? new Date(parseDateOne(dateFin)) : null;

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
            $('#date-debut').val('');
            $('#date-fin').val('');
            $('#mois-annee').val('');

            // Supprimer tous les filtres personnalisés
            $.fn.dataTable.ext.search = [];

            // Réinitialiser les recherches par colonne
            // table.columns().search('').draw();
            table.draw();
        });
    });