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
                <h3 class="content-header-title">
                    Creation des tarifs - ${trajet.gareDepart.ville}  ${trajet.gareArrivee.ville}
                </h3>
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

                                    <form method="post" action="${pageContext.request.contextPath}/tarifs/ajouter"
                                          class="form form-horizontal form-bordered">

                                        <input type="hidden" name="idTrajet" value="${trajet.id}"/>

                                        <table class="table table-striped table-bordered">
                                            <thead>
                                            <tr>
                                                <th>Type de voyage</th>
                                                <th>Montant (Ar)</th>
                                            </tr>
                                            </thead>
                                            <tbody>
                                            <c:forEach var="t" items="${typesVoyage}">
                                                <tr>
                                                    <td>${t.description}</td>
                                                    <td>
                                                        <input type="number" step="0.01" min="0"
                                                               class="form-control"
                                                               name="montant_${t.id}"
                                                               placeholder="Montant pour ${t.description}">
                                                    </td>
                                                </tr>
                                            </c:forEach>
                                            </tbody>
                                        </table>

                                        <div class="form-actions text-right">
                                            <button type="submit" class="btn btn-primary">
                                                <i class="la la-check-square-o"></i> Enregistrer les tarifs
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
