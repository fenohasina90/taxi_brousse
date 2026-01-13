<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>
<%@ taglib prefix="fmt" uri="http://java.sun.com/jsp/jstl/fmt" %>
<%@ include file="/WEB-INF/jsp/layout/header.jsp" %>

<body class="vertical-layout vertical-menu 2-columns fixed-navbar"
      data-open="click" data-menu="vertical-menu" data-col="2-columns">

<%@ include file="/WEB-INF/jsp/layout/sidebar.jsp" %>

<div class="app-content content">
    <div class="content-overlay"></div>
    <div class="content-wrapper">

        <!-- Header -->
        <div class="content-header row">
            <div class="content-header-left col-md-6 col-12 mb-2">
                <h3 class="content-header-title">Liste des voyages</h3>
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

                                    <!-- FORMULAIRE DE FILTRE -->
                                    <div id="filters-panel">
                                    <form method="get" action="${pageContext.request.contextPath}/reservations/ajouter"
                                          class="form form-horizontal form-bordered">

                                        <div class="form-body">

                                            <!-- Ligne 1 : ville depart, ville arrivee, dates et heures -->
                                            <div class="form-group row">
                                                <div class="col-md-2">
                                                    <label class="label-control">Ville de depart</label>
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

                                                <div class="col-md-2">
                                                    <label class="label-control">Ville d'arrivee</label>
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

                                                <div class="col-md-2">
                                                    <label class="label-control">Date debut</label>
                                                    <input type="date" class="form-control" name="dateDebut"
                                                           value="${param.dateDebut}">
                                                </div>

                                                <div class="col-md-2">
                                                    <label class="label-control">Date fin</label>
                                                    <input type="date" class="form-control" name="dateFin"
                                                           value="${param.dateFin}">
                                                </div>

                                                <div class="col-md-2">
                                                    <label class="label-control">Heure debut</label>
                                                    <input type="time" class="form-control" name="heureDebut"
                                                           value="${param.heureDebut}">
                                                </div>

                                                <div class="col-md-2">
                                                    <label class="label-control">Heure fin</label>
                                                    <input type="time" class="form-control" name="heureFin"
                                                           value="${param.heureFin}">
                                                </div>
                                            </div>

                                            <!-- Ligne 2 : type, tarif, places -->
                                            <div class="form-group row">
                                                <div class="col-md-3">
                                                    <label class="label-control">Type du voyage</label>
                                                    <select class="form-control" name="idTypeVoyage">
                                                        <option value="">-- Tous --</option>
                                                        <c:forEach var="s" items="${statusVoyages}">
                                                            <option value="${s.id}"
                                                                    ${param.idTypeVoyage == s.id ? 'selected' : ''}>
                                                                ${s.description}
                                                            </option>
                                                        </c:forEach>
                                                    </select>
                                                </div>

                                                <div class="col-md-3">
                                                    <label class="label-control">Tarif minimum</label>
                                                    <input type="number" step="0.01" class="form-control" name="tarifMin"
                                                           value="${param.tarifMin}" placeholder="Tarif minimum ...">
                                                </div>

                                                <div class="col-md-3">
                                                    <label class="label-control">Tarif maximum</label>
                                                    <input type="number" step="0.01" class="form-control" name="tarifMax"
                                                           value="${param.tarifMax}" placeholder="Tarif maximum ...">
                                                </div>

                                                <div class="col-md-3">
                                                    <label class="label-control">Places disponibles</label>
                                                    <div class="row">
                                                        <div class="col-6 pr-1">
                                                            <input type="number" class="form-control" name="placesMin"
                                                                   value="${param.placesMin}" placeholder="Min">
                                                        </div>
                                                        <div class="col-6 pl-1">
                                                            <input type="number" class="form-control" name="placesMax"
                                                                   value="${param.placesMax}" placeholder="Max">
                                                        </div>
                                                    </div>
                                                </div>
                                            </div>

                                            <!-- Boutons -->
                                            <div class="form-group row">
                                                <div class="col-md-12 text-right">
                                                    <button type="submit" class="btn btn-primary">
                                                        <i class="la la-filter"></i> Filtrer
                                                    </button>
                                                    <a href="${pageContext.request.contextPath}/reservations/ajouter"
                                                       class="btn btn-secondary">
                                                        Reinitialiser
                                                    </a>
                                                </div>
                                            </div>

                                        </div>
                                    </form>
                                    <hr/>
                                    </div>

                                    <!-- TABLEAU -->
                                    <c:if test="${empty voyages}">
                                        <p class="text-center text-muted">
                                            Aucun voyage trouve.
                                        </p>
                                    </c:if>

                                    <c:if test="${not empty voyages}">
                                        <table class="table table-striped table-bordered">
                                            <thead>
                                                <tr>
                                                    <th>Trajet</th>
                                                    <th>Date depart</th>
                                                    <th>Heure depart</th>
                                                    <!-- <th>Estimation trajet</th>
                                                    <th>Distance</th> -->
                                                    <th>Voiture</th>
                                                    <th>Place Disponible</th>
                                                    <th>Type Voyage</th>
                                                    <th>Tarif (Ar)</th>
                                                    <th>Action</th>
                                                </tr>
                                            </thead>
                                            <tbody>
                                                <c:forEach var="v" items="${voyages}">
                                                    <tr>
                                                        <td>${v.parcours}</td>
                                                        <td>${v.dateDepart}</td>
                                                        <td>${v.heureDepart}</td>
                                                        <!-- <td>${v.dureeEstimee}</td>
                                                        <td>${v.distance}</td> -->
                                                        <td>${v.immatricule}</td>
                                                        <td>${v.placesDisponibles}</td>
                                                        <td>
                                                            <span class="badge badge-info">
                                                                ${v.typeVoyage}
                                                            </span>
                                                        </td>
                                                        <td>${v.tarif}</td>
                                                        <td>
                                                            <a href="/reservations/ajouter/${v.idVoyageDetails}" class="btn btn-primary btn-sm">Reserver</a>
                                                        </td>
                                                    </tr>
                                                </c:forEach>
                                            </tbody>
                                        </table>
                                    </c:if>

                                </div>
                            </div>
                        </div>

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

<%@ include file="/WEB-INF/jsp/layout/footer.jsp" %>
</body>
</html>
