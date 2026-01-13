<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>
<%@ include file="/WEB-INF/jsp/layout/header.jsp"%>

<body class="vertical-layout vertical-menu 2-columns fixed-navbar"
    data-open="click" data-menu="vertical-menu" data-col="2-columns">

<%@ include file="/WEB-INF/jsp/layout/sidebar.jsp"%>

<div class="app-content content">
    <div class="content-overlay"></div>
    <div class="content-wrapper">

        <div class="content-header row">
            <div class="content-header-left col-md-6 col-12 mb-2">
                <h3 class="content-header-title">Liste des reservations</h3>
            </div>
        </div>

        <div class="content-body">
            <section id="basic-form-layouts">
                <div class="row">
                    <div class="col-md-12">

                        <!-- Carte filtres -->
                        <div class="card">
                            <div class="card-header d-flex justify-content-between align-items-center">
                                <h4 class="card-title mb-0">Filtres</h4>
                                <button type="button" class="btn btn-sm btn-outline-primary" id="toggle-filters-btn">
                                    Masquer les filtres
                                </button>
                            </div>
                            <div class="card-content collpase show">
                                <div class="card-body">

                                    <div id="filters-panel">
                                        <form method="get" action="${pageContext.request.contextPath}/reservations"
                                            class="form form-horizontal form-bordered">

                                            <div class="form-body">

                                                <div class="form-group row">
                                                    <label class="col-md-2 label-control">Ville depart</label>
                                                    <div class="col-md-4">
                                                        <select class="form-control" name="idGareDepart">
                                                            <option value="">-- Toutes --</option>
                                                            <c:forEach var="v" items="${villesDepart}">
                                                                <option value="${v.id}"
                                                                        ${param.idGareDepart == v.id ? 'selected' : ''}>
                                                                    ${v.ville} (${v.nom})
                                                                </option>
                                                            </c:forEach>
                                                        </select>
                                                    </div>

                                                    <label class="col-md-2 label-control">Ville arrivee</label>
                                                    <div class="col-md-4">
                                                        <select class="form-control" name="idGareArrivee">
                                                            <option value="">-- Toutes --</option>
                                                            <c:forEach var="v" items="${villesArrivee}">
                                                                <option value="${v.id}"
                                                                        ${param.idGareArrivee == v.id ? 'selected' : ''}>
                                                                    ${v.ville} (${v.nom})
                                                                </option>
                                                            </c:forEach>
                                                        </select>
                                                    </div>
                                                </div>

                                                <div class="form-group row">
                                                    <label class="col-md-2 label-control">Date depart</label>
                                                    <div class="col-md-4">
                                                        <input type="date" class="form-control" name="dateDebut"
                                                            value="${param.dateDebut}">
                                                    </div>
                                                    <div class="col-md-4 offset-md-2">
                                                        <input type="date" class="form-control" name="dateFin"
                                                            value="${param.dateFin}">
                                                    </div>
                                                </div>

                                                <div class="form-group row">
                                                    <label class="col-md-2 label-control">Heure depart</label>
                                                    <div class="col-md-4">
                                                        <input type="time" class="form-control" name="heureDebut"
                                                            value="${param.heureDebut}">
                                                    </div>
                                                    <div class="col-md-4 offset-md-2">
                                                        <input type="time" class="form-control" name="heureFin"
                                                            value="${param.heureFin}">
                                                    </div>
                                                </div>

                                                <div class="form-group row">
                                                    <div class="col-md-12 text-right">
                                                        <button type="submit" class="btn btn-primary">
                                                            <i class="la la-filter"></i> Filtrer
                                                        </button>
                                                        <a href="${pageContext.request.contextPath}/reservations" class="btn btn-secondary">
                                                            Reinitialiser
                                                        </a>
                                                    </div>
                                                </div>

                                            </div>

                                        </form>
                                        <hr/>
                                    </div>

                                </div>
                            </div>
                        </div>

                        <!-- Carte liste -->
                        <div class="card">
                            <div class="card-header">
                                <h4 class="card-title">Reservations</h4>
                            </div>
                            <div class="card-content">
                                <div class="card-body">

                                    <c:if test="${empty reservations}">
                                        <p>Aucune reservation trouvee pour les criteres selectionnes.</p>
                                    </c:if>

                                    <c:if test="${not empty reservations}">
                                        <table class="table table-striped table-bordered">
                                            <thead>
                                            <tr>
                                                <th>ID reservation</th>
                                                <th>Nom client</th>
                                                <th>Contact</th>
                                                <th>Trajet</th>
                                                <th>Date reservation</th>
                                                <th>Total (Ar)</th>
                                                <th>Statut</th>
                                                <th>Action</th>
                                            </tr>
                                            </thead>
                                            <tbody>
                                            <c:forEach var="r" items="${reservations}">
                                                <tr>
                                                    <td>${r.idReservation}</td>
                                                    <td>${r.nomClient}</td>
                                                    <td>${r.contactClient}</td>
                                                    <td>${r.trajet}</td>
                                                    <td>${r.reservationDate}</td>
                                                    <td>${r.totalAmount}</td>
                                                    <td>${r.statusReservation}</td>
                                                    <td>
                                                        <!-- Bouton Faire paiement (à brancher sur un flux de paiement dedie ulterieurement) -->
                                                        <button type="button" class="btn btn-sm btn-success" disabled>
                                                            Faire paiement
                                                        </button>

                                                        <!-- Bouton Annuler la reservation -->
                                                        <form method="post" action="${pageContext.request.contextPath}/reservations/${r.idReservation}/annuler" style="display:inline-block;">
                                                            <button type="submit" class="btn btn-sm btn-danger"
                                                                    onclick="return confirm('Confirmer l\'annulation de cette reservation ?');">
                                                                Annuler
                                                            </button>
                                                        </form>

                                                        <!-- Bouton Voir places reservees -->
                                                        <button type="button" class="btn btn-sm btn-warning"
                                                                data-toggle="modal"
                                                                data-target="#placesModal_${r.idReservation}">
                                                            Voir places reservees
                                                        </button>

                                                        <!-- Bouton Voir details -->
                                                        <button type="button" class="btn btn-sm btn-info"
                                                                data-toggle="modal"
                                                                data-target="#detailsModal_${r.idReservation}">
                                                            Voir details
                                                        </button>
                                                    </td>
                                                </tr>
                                            </c:forEach>
                                            </tbody>
                                        </table>
                                    </c:if>

                                </div>
                            </div>
                        </div>

                        <!-- Modals details et places -->
                        <c:forEach var="r" items="${reservations}">
                            <!-- Modal details reservation -->
                            <div class="modal fade" id="detailsModal_${r.idReservation}" tabindex="-1" role="dialog" aria-hidden="true">
                                <div class="modal-dialog modal-lg" role="document">
                                    <div class="modal-content">
                                        <div class="modal-header">
                                            <h5 class="modal-title">Details reservation #${r.idReservation}</h5>
                                            <button type="button" class="close" data-dismiss="modal" aria-label="Close">
                                                <span aria-hidden="true">&times;</span>
                                            </button>
                                        </div>
                                        <div class="modal-body">
                                            <table class="table table-sm table-striped table-bordered mb-0">
                                                <tbody>
                                                <tr>
                                                    <th>Trajet</th>
                                                    <td>${r.trajet}</td>
                                                </tr>
                                                <tr>
                                                    <th>Date voyage</th>
                                                    <td>${r.dateDepart}</td>
                                                </tr>
                                                <tr>
                                                    <th>Heure depart</th>
                                                    <td>${r.heureDepart}</td>
                                                </tr>
                                                <tr>
                                                    <th>Nombre de places reservees</th>
                                                    <td>${r.nbPlaceReserve}</td>
                                                </tr>
                                                <tr>
                                                    <th>Voiture</th>
                                                    <td>${r.immatricule}</td>
                                                </tr>
                                                <tr>
                                                    <th>Capacite totale</th>
                                                    <td>${r.capaciteTotale}</td>
                                                </tr>
                                                <tr>
                                                    <th>Type de voyage</th>
                                                    <td>${r.typeVoyage}</td>
                                                </tr>
                                                <tr>
                                                    <th>Tarif unitaire (Ar)</th>
                                                    <td>${r.tarifUnitaire}</td>
                                                </tr>
                                                </tbody>
                                            </table>
                                        </div>
                                        <div class="modal-footer">
                                            <button type="button" class="btn btn-secondary" data-dismiss="modal">Fermer</button>
                                        </div>
                                    </div>
                                </div>
                            </div>

                            <!-- Modal places reservees avec dessin des sieges -->
                            <div class="modal fade" id="placesModal_${r.idReservation}" tabindex="-1" role="dialog" aria-hidden="true">
                                <div class="modal-dialog" role="document">
                                    <div class="modal-content">
                                        <div class="modal-header">
                                            <h5 class="modal-title">Places reservees - Reservation #${r.idReservation}</h5>
                                            <button type="button" class="close" data-dismiss="modal" aria-label="Close">
                                                <span aria-hidden="true">&times;</span>
                                            </button>
                                        </div>
                                        <div class="modal-body">
                                            <c:set var="placesReservation" value="${placesParReservation[r.idReservation]}" />

                                            <c:if test="${empty placesReservation}">
                                                <p>Aucune place enregistree pour cette reservation.</p>
                                            </c:if>

                                            <c:if test="${not empty placesReservation}">
                                                <div class="seat-container" style="transform: scale(0.8); transform-origin: top left;">

                                                    <!-- Premiere ligne : chauffeur + places 1-2 -->
                                                    <div class="seat-row">
                                                        <label class="seat-disabled chauffeur">
                                                            <input type="checkbox" disabled>
                                                            <span>chauffeur</span>
                                                        </label>

                                                        <c:forEach var="i" begin="1" end="${r.capaciteTotale >= 2 ? 2 : r.capaciteTotale}">
                                                            <c:set var="reserve" value="false" />
                                                            <c:forEach var="p" items="${placesReservation}">
                                                                <c:if test="${p == i}">
                                                                    <c:set var="reserve" value="true" />
                                                                </c:if>
                                                            </c:forEach>

                                                            <label class="seat ${reserve ? 'seat-selected' : ''}">
                                                                <input type="checkbox"
                                                                    class="seat-checkbox"
                                                                    disabled
                                                                    ${reserve ? 'checked' : ''}>
                                                                <span>${i}</span>
                                                            </label>
                                                        </c:forEach>
                                                    </div>

                                                    <!-- Lignes suivantes : 3-6, 7-10, ... -->
                                                    <c:if test="${r.capaciteTotale > 2}">
                                                        <c:forEach var="start" begin="3" end="${r.capaciteTotale}" step="4">
                                                            <div class="seat-row">
                                                                <c:forEach var="i" begin="${start}" end="${start + 3 <= r.capaciteTotale ? start + 3 : r.capaciteTotale}">

                                                                    <c:set var="reserve" value="false" />
                                                                    <c:forEach var="p" items="${placesReservation}">
                                                                        <c:if test="${p == i}">
                                                                            <c:set var="reserve" value="true" />
                                                                        </c:if>
                                                                    </c:forEach>

                                                                    <label class="seat ${reserve ? 'seat-selected' : ''}">
                                                                        <input type="checkbox"
                                                                            class="seat-checkbox"
                                                                            disabled
                                                                            ${reserve ? 'checked' : ''}>
                                                                        <span>${i}</span>
                                                                    </label>

                                                                </c:forEach>
                                                            </div>
                                                        </c:forEach>
                                                    </c:if>

                                                </div>
                                            </c:if>
                                        </div>
                                        <div class="modal-footer">
                                            <button type="button" class="btn btn-secondary" data-dismiss="modal">Fermer</button>
                                        </div>
                                    </div>
                                </div>
                            </div>
                        </c:forEach>

                    </div>
                </div>
            </section>
        </div>
    </div>
</div>

<script type="text/javascript">
    (function () {
        var panel = document.getElementById('filters-panel');
        var btn = document.getElementById('toggle-filters-btn');
        if (!panel || !btn) {
            return;
        }

        var visible = true;

        btn.addEventListener('click', function () {
            visible = !visible;
            panel.style.display = visible ? '' : 'none';
            btn.innerText = visible ? 'Masquer les filtres' : 'Afficher les filtres';
        });
    })();
</script>

<%@ include file="/WEB-INF/jsp/layout/footer.jsp"%>
</body>
</html>
