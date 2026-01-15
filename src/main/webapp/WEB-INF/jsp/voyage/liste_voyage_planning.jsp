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
                <h3 class="content-header-title">Planning des voyages</h3>
            </div>
            <div class="content-header-right col-md-6 col-12 mb-2 text-right">
                <a href="${pageContext.request.contextPath}/voyages/ajouter" class="btn btn-primary">
                    <i class="la la-plus"></i> Creer un voyage
                </a>
            </div>
        </div>

        <div class="content-body">
            <section id="basic-form-layouts">
                <div class="row">
                    <div class="col-md-12">

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
                                    <form method="get" action="${pageContext.request.contextPath}/voyages/planning"
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
                                                    <a href="${pageContext.request.contextPath}/voyages/planning" class="btn btn-secondary">
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

                        <div class="card">
                            <div class="card-header">
                                <h4 class="card-title">Liste des voyages</h4>
                            </div>
                            <div class="card-content">
                                <div class="card-body">

                                    <c:if test="${empty voyages}">
                                        <p>Aucun voyage trouve pour les criteres selectionnes.</p>
                                    </c:if>

                                    <c:if test="${not empty voyages}">
                                        <table class="table table-striped table-bordered">
                                            <thead>
                                            <tr>
                                                <th>Trajet</th>
                                                <th>Date depart</th>
                                                <th>Total voyage details</th>
                                                <th>Total chiffre d'affaire (Ar)</th>
                                                <th>Action</th>
                                            </tr>
                                            </thead>
                                            <tbody>
                                            <c:forEach var="v" items="${voyages}">
                                                <tr>
                                                    <td>${v.trajet}</td>
                                                    <td>${v.dateDepart}</td>
                                                    <td>${v.totalVoyageDetails}</td>
                                                    <td>${v.totalChiffreAffaire}</td>
                                                    <td>
                                                        <a href="${pageContext.request.contextPath}/voyages/${v.idVoyage}/details/ajouter"
                                                           class="btn btn-sm btn-success">Ajouter voyage details</a>

                                                        <button type="button" class="btn btn-sm btn-info"
                                                                data-toggle="modal"
                                                                data-target="#detailsModal_${v.idVoyage}">
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

                        <!-- Modals pour les details -->
                        <c:forEach var="v" items="${voyages}">
                            <div class="modal fade" id="detailsModal_${v.idVoyage}" tabindex="-1" role="dialog" aria-hidden="true">
                                <div class="modal-dialog modal-xl" role="document">
                                    <div class="modal-content">
                                        <div class="modal-header">
                                            <h5 class="modal-title">Details du voyage - ${v.trajet} (${v.dateVoyage})</h5>
                                            <button type="button" class="close" data-dismiss="modal" aria-label="Close">
                                                <span aria-hidden="true">&times;</span>
                                            </button>
                                        </div>
                                        <div class="modal-body">
                                            <c:set var="details" value="${detailsParVoyage[v.idVoyage]}" />
                                            <c:if test="${empty details}">
                                                <p>Aucun voyage details pour ce voyage.</p>
                                            </c:if>
                                            <c:if test="${not empty details}">
                                                    <table class="table table-sm table-striped table-bordered mb-0" style="font-size: 0.85rem;">
                                                        <thead>
                                                        <tr>
                                                            <th>Heure depart</th>
                                                            <th>Voiture</th>
                                                            <th>Places totales</th>
                                                            <th>Place disponible</th>
                                                            <th>Total reservations</th>
                                                            <!-- <th>Type voyage</th>
                                                            <th>Tarif (Ar)</th> -->
                                                            <th>Total chiffre d'affaire (Ar)</th>
                                                            <th>CA maximum (Ar)</th>
                                                        </tr>
                                                        </thead>
                                                        <tbody>
                                                        <c:forEach var="d" items="${details}">
                                                            <tr>
                                                                <td>${d.heureDepart}</td>
                                                                <td>${d.voiture}</td>
                                                                <td>${d.nbPlaceTotal}</td>
                                                                <td>${d.placesDisponibles}</td>
                                                                <td>${d.nombreReservations}</td>
                                                                <!-- <td>${d.typeVoyage}</td>
                                                                <td>${d.tarif}</td> -->
                                                                <td>
                                                                    <fmt:formatNumber value="${d.totalChiffreAffaire}" type="number" pattern="#,##0.00" />
                                                                </td>
                                                                <td>
                                                                    <fmt:formatNumber value="${d.maxChiffreAffaire}" type="number" pattern="#,##0.00" />
                                                                </td>
                                                            </tr>
                                                        </c:forEach>
                                                        </tbody>
                                                    </table>
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
