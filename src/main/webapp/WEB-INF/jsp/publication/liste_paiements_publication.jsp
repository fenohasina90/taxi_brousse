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
                <h3 class="content-header-title">Paiements publications</h3>
            </div>
        </div>

        <div class="content-body">
            <section id="basic-form-layouts">
                <div class="row">
                    <div class="col-md-12">

                        <c:if test="${not empty successMessage}">
                            <div class="alert alert-success">${successMessage}</div>
                        </c:if>
                        <c:if test="${not empty errorMessage}">
                            <div class="alert alert-danger">${errorMessage}</div>
                        </c:if>

                        <div class="card">
                            <div class="card-header">
                                <h4 class="card-title">Filtres</h4>
                            </div>
                            <div class="card-content collpase show">
                                <div class="card-body">
                                    <form method="get" action="${pageContext.request.contextPath}/publications/paiements"
                                          class="form form-horizontal form-bordered">
                                        <div class="form-body">
                                            <div class="form-group row">
                                                <label class="col-md-2 label-control">Date debut</label>
                                                <div class="col-md-4">
                                                    <input type="date" class="form-control" name="dateDebut" value="${param.dateDebut}">
                                                </div>
                                                <label class="col-md-2 label-control">Date fin</label>
                                                <div class="col-md-4">
                                                    <input type="date" class="form-control" name="dateFin" value="${param.dateFin}">
                                                </div>
                                            </div>
                                            <div class="form-group row">
                                                <div class="col-md-12 text-right">
                                                    <button type="submit" class="btn btn-primary">
                                                        <i class="la la-filter"></i> Filtrer
                                                    </button>
                                                    <a href="${pageContext.request.contextPath}/publications/paiements" class="btn btn-secondary">
                                                        Reinitialiser
                                                    </a>
                                                </div>
                                            </div>
                                        </div>
                                    </form>
                                </div>
                            </div>
                        </div>

                        <div class="card">
                            <div class="card-header">
                                <h4 class="card-title">Etat de paiement par diffusion</h4>
                            </div>
                            <div class="card-content">
                                <div class="card-body">
                                    <c:if test="${empty etatPaiement}">
                                        <p>Aucune diffusion trouvee.</p>
                                    </c:if>

                                    <c:if test="${not empty etatPaiement}">
                                        <table class="table table-striped table-bordered">
                                            <thead>
                                            <tr>
                                                <th>Date voyage</th>
                                                <th>Heure depart</th>
                                                <th>Societe</th>
                                                <th>Publication</th>
                                                <th>Repetition</th>
                                                <th>Total a payer</th>
                                                <th>Deja paye</th>
                                                <th>Reste</th>
                                                <th>Action</th>
                                            </tr>
                                            </thead>
                                            <tbody>
                                            <c:forEach var="e" items="${etatPaiement}">
                                                <tr>
                                                    <td>${e.dateVoyage}</td>
                                                    <td>${e.heureDepart}</td>
                                                    <td>${e.societe}</td>
                                                    <td>${e.titre}</td>
                                                    <td>${e.nbRepetition}</td>
                                                    <td>${e.totalAPayer}</td>
                                                    <td>${e.montantPaye}</td>
                                                    <td>${e.resteAPayer}</td>
                                                    <td>
                                                        <a class="btn btn-sm btn-success"
                                                           href="${pageContext.request.contextPath}/publications/paiements/ajouter?idVoyagePub=${e.idVoyagePub}">
                                                            Payer
                                                        </a>
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

<%@ include file="/WEB-INF/jsp/layout/footer.jsp"%>
</body>
</html>
