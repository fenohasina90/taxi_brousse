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
                <h3 class="content-header-title">Creer un trajet</h3>
            </div>
        </div>

        <div class="content-body">
            <section id="basic-form-layouts">
                <div class="row">
                    <div class="col-md-12">

                        <div class="card">
                            <div class="card-header"></div>
                            <div class="card-content collpase show">
                                <div class="card-body">

                                    <c:if test="${not empty errorMessage}">
                                        <div class="alert alert-danger">${errorMessage}</div>
                                    </c:if>

                                    <form method="post" action="${pageContext.request.contextPath}/trajets/ajouter"
                                          class="form form-horizontal form-bordered">

                                        <div class="form-body">

                                            <div class="form-group row">
                                                <label class="col-md-2 label-control">Ville depart</label>
                                                <div class="col-md-4">
                                                    <select class="form-control" name="idGareDepart" required>
                                                        <option value="">-- Choisir --</option>
                                                        <c:forEach var="g" items="${gares}">
                                                            <option value="${g.id}">${g.ville} (${g.nom})</option>
                                                        </c:forEach>
                                                    </select>
                                                </div>

                                                <label class="col-md-2 label-control">Ville arrivee</label>
                                                <div class="col-md-4">
                                                    <select class="form-control" name="idGareArrivee" required>
                                                        <option value="">-- Choisir --</option>
                                                        <c:forEach var="g" items="${gares}">
                                                            <option value="${g.id}">${g.ville} (${g.nom})</option>
                                                        </c:forEach>
                                                    </select>
                                                </div>
                                            </div>

                                            <div class="form-group row">
                                                <label class="col-md-2 label-control">Distance (km)</label>
                                                <div class="col-md-4">
                                                    <input type="number" step="0.1" class="form-control"
                                                           name="distanceKm" placeholder="Optionnel">
                                                </div>

                                                <label class="col-md-2 label-control">Estimation (heures)</label>
                                                <div class="col-md-4">
                                                    <input type="number" class="form-control"
                                                           name="estimationHeure" placeholder="Optionnel">
                                                </div>
                                            </div>

                                        </div>

                                        <div class="form-actions text-right">
                                            <button type="submit" class="btn btn-primary">
                                                <i class="la la-check-square-o"></i> Suivant (tarifs)
                                            </button>
                                        </div>

                                    </form>

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
