<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>
<%@ taglib prefix="fmt" uri="http://java.sun.com/jsp/jstl/fmt" %>
<%@ include file="/WEB-INF/jsp/layout/header.jsp"%>

<body class="vertical-layout vertical-menu 2-columns fixed-navbar"
      data-open="click" data-menu="vertical-menu" data-col="2-columns">

<%@ include file="/WEB-INF/jsp/layout/sidebar.jsp"%>

<div class="app-content content">
    <div class="content-overlay"></div>
    <div class="content-wrapper">

        <div class="content-header row">
            <div class="content-header-left col-md-6 col-12 mb-2">
                <h3 class="content-header-title">Chiffre d'affaires - Diffusions publications</h3>
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
                                        <form method="get" action="${pageContext.request.contextPath}/publications/chiffre-affaire"
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
                                                    <div class="col-md-12 text-right">
                                                        <button type="submit" class="btn btn-primary">
                                                            <i class="la la-filter"></i> Filtrer
                                                        </button>
                                                        <a href="${pageContext.request.contextPath}/publications/chiffre-affaire" class="btn btn-secondary">
                                                            Reinitialiser
                                                        </a>
                                                    </div>
                                                </div>

                                            </div>

                                        </form>
                                        <hr/>
                                    </div>

                                    <c:if test="${not empty errorMessage}">
                                        <div class="alert alert-danger">${errorMessage}</div>
                                    </c:if>

                                </div>
                            </div>
                        </div>

                        <div class="card">
                            <div class="card-header d-flex justify-content-between align-items-center">
                                <h4 class="card-title mb-0">Resultats</h4>
                                <div>
                                    <strong>Total CA:</strong> <fmt:formatNumber value="${totalCa}" type="number" pattern="#,##0.00" />
                                    &nbsp;|&nbsp;
                                    <strong>Deja paye:</strong> <fmt:formatNumber value="${totalPaye}" type="number" pattern="#,##0.00" />
                                    &nbsp;|&nbsp;
                                    <strong>Reste a payer:</strong> <fmt:formatNumber value="${totalReste}" type="number" pattern="#,##0.00" />
                                </div>
                            </div>
                            <div class="card-content">
                                <div class="card-body">

                                    <c:if test="${empty caPublications}">
                                        <p>Aucune diffusion trouvee pour les criteres selectionnes.</p>
                                    </c:if>

                                    <c:if test="${not empty caPublications}">
                                        <table class="table table-striped table-bordered">
                                            <thead>
                                            <tr>
                                                <th>Date voyage</th>
                                                <th>Societe</th>
                                                <th>Titre</th>
                                                <th>Total repetition</th>
                                                <th>Montant unitaire (Ar)</th>
                                                <th>Chiffre d'affaires (Ar)</th>
                                                <th>Montant paye (Ar)</th>
                                                <th>Reste a payer (Ar)</th>
                                            </tr>
                                            </thead>
                                            <tbody>
                                            <c:forEach var="r" items="${caPublications}">
                                                <tr>
                                                    <td>${r.dateVoyage}</td>
                                                    <td>${r.societe}</td>
                                                    <td>${r.titre}</td>
                                                    <td>${r.totalRepetition}</td>
                                                    <td>
                                                        <fmt:formatNumber value="${r.montantUnitaire}" type="number" pattern="#,##0.00" />
                                                    </td>
                                                    <td>
                                                        <fmt:formatNumber value="${r.chiffreAffaires}" type="number" pattern="#,##0.00" />
                                                    </td>
                                                    <td>
                                                        <fmt:formatNumber value="${r.montantPaye}" type="number" pattern="#,##0.00" />
                                                    </td>
                                                    <td>
                                                        <fmt:formatNumber value="${r.resteAPayer}" type="number" pattern="#,##0.00" />
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
