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
                <h3 class="content-header-title">Liste des tarifs actuels</h3>
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
                                        <form method="get" action="${pageContext.request.contextPath}/tarifs"
                                              class="form form-horizontal form-bordered">

                                            <div class="form-body">

                                                <div class="form-group row">
                                                    <label class="col-md-2 label-control">Date debut</label>
                                                    <div class="col-md-4">
                                                        <input type="date" class="form-control" name="dateDebut"
                                                               value="${param.dateDebut}">
                                                    </div>
                                                    <label class="col-md-2 label-control">Date fin</label>
                                                    <div class="col-md-4">
                                                        <input type="date" class="form-control" name="dateFin"
                                                               value="${param.dateFin}">
                                                    </div>
                                                </div>

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
                                                    <label class="col-md-2 label-control">Type de voyage</label>
                                                    <div class="col-md-4">
                                                        <select class="form-control" name="idTypeVoyage">
                                                            <option value="">-- Tous --</option>
                                                            <c:forEach var="t" items="${typesVoyage}">
                                                                <option value="${t.id}"
                                                                        ${param.idTypeVoyage == t.id ? 'selected' : ''}>
                                                                    ${t.description}
                                                                </option>
                                                            </c:forEach>
                                                        </select>
                                                    </div>
                                                </div>

                                                <div class="form-group row">
                                                    <div class="col-md-12 text-right">
                                                        <button type="submit" class="btn btn-primary">
                                                            <i class="la la-filter"></i> Filtrer
                                                        </button>
                                                        <a href="${pageContext.request.contextPath}/tarifs" class="btn btn-secondary">
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
                                <h4 class="card-title">Tarifs actuels</h4>
                            </div>
                            <div class="card-content">
                                <div class="card-body">

                                    <c:if test="${empty tarifs}">
                                        <p>Aucun tarif trouve pour les criteres selectionnes.</p>
                                    </c:if>

                                    <c:if test="${not empty tarifs}">
                                        <table class="table table-striped table-bordered">
                                            <thead>
                                            <tr>
                                                <th>Trajet</th>
                                                <th>Date debut</th>
                                                <th>Type de voyage</th>
                                                <th>Montant (Ar)</th>
                                                <th>Action</th>
                                            </tr>
                                            </thead>
                                            <tbody>
                                            <c:forEach var="t" items="${tarifs}">
                                                <tr>
                                                    <td>${t.trajet}</td>
                                                    <td>${t.daty}</td>
                                                    <td>${t.typeVoyage}</td>
                                                    <td>${t.montant}</td>
                                                    <td>
                                                        <a href="${pageContext.request.contextPath}/tarifs/${t.idTarif}/modifier"
                                                           class="btn btn-sm btn-warning">Modifier tarif</a>
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

<%@ include file="/WEB-INF/jsp/layout/footer.jsp"%>
</body>
</html>
