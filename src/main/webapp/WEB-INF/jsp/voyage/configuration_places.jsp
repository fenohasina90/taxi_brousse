<%@ page contentType="text/html;charset=UTF-8" language="java" %>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>

<%@ include file="/WEB-INF/jsp/layout/header.jsp"%>

<body class="vertical-layout vertical-menu 2-columns fixed-navbar"
      data-open="click" data-menu="vertical-menu" data-col="2-columns">

<%@ include file="/WEB-INF/jsp/layout/sidebar.jsp"%>

<div class="app-content content">
    <div class="content-overlay"></div>
    <div class="content-wrapper">

        <!-- Header -->
        <div class="content-header row">
            <div class="content-header-left col-md-6 col-12 mb-2">
                <h3 class="content-header-title">Configuration des places du voyage</h3>
            </div>
            <div class="content-header-right col-md-6 col-12 mb-2 text-right">
                <a href="${pageContext.request.contextPath}/voyages" class="btn btn-secondary">
                    <i class="la la-list"></i> Liste des voyages
                </a>
            </div>
        </div>

        <div class="content-body">
            <section id="basic-form-layouts">
                <div class="row">
                    <div class="col-md-12">

                        <div class="card">
                            <div class="card-header">
                                <div class="heading-elements">
                                    <ul class="list-inline mb-0">
                                        <li><a data-action="collapse"><i class="ft-minus"></i></a></li>
                                        <li><a data-action="reload"><i class="ft-rotate-cw"></i></a></li>
                                        <li><a data-action="expand"><i class="ft-maximize"></i></a></li>
                                        <li><a data-action="close"><i class="ft-x"></i></a></li>
                                    </ul>
                                </div>
                            </div>

                            <div class="card-content collpase show">
                                <div class="card-body">

                                    <!-- MESSAGE D'ERREUR -->
                                    <c:if test="${not empty errorMessage}">
                                        <div class="alert alert-danger">
                                            ${errorMessage}
                                        </div>
                                    </c:if>

                                    <!-- FORMULAIRE CONFIGURATION PLACES -->
                                    <form class="form form-horizontal form-bordered"
                                          method="post"
                                          action="${pageContext.request.contextPath}/voyages/details/${idVoyageDetails}/config-places">

                                        <div class="form-body">

                                            <p class="text-muted mb-2" style="font-size: 0.9rem;">
                                                Pour chaque type de voyage ci-dessous, sélectionnez les sièges
                                                correspondants. Un siège ne doit appartenir qu'à un seul type.
                                            </p>

                                            <c:forEach var="t" items="${typesVoyage}">
                                                <div class="form-group row">
                                                    <label class="col-md-3 label-control">
                                                        Type : ${t.description}
                                                    </label>

                                                    <div class="col-md-9">
                                                        <div class="seat-container">

                                                            <!-- Première ligne : case chauffeur + places 1 et 2 -->
                                                            <div class="seat-row">
                                                                <!-- Case chauffeur (désactivée) -->
                                                                <label class="seat-disabled chauffeur">
                                                                    <input type="checkbox" disabled>
                                                                    <span>chauffeur</span>
                                                                </label>

                                                                <!-- Places 1 et 2 -->
                                                                <c:forEach var="i" begin="1" end="${totalPlaces >= 2 ? 2 : totalPlaces}">
                                                                    <label class="seat">
                                                                        <input type="checkbox"
                                                                               class="seat-checkbox"
                                                                               name="places_${t.id}"
                                                                               value="${i}">
                                                                        <span>${i}</span>
                                                                    </label>
                                                                </c:forEach>
                                                            </div>

                                                            <!-- Lignes suivantes : à partir de la place 3, par blocs de 4 -->
                                                            <c:if test="${totalPlaces > 2}">
                                                                <c:forEach var="start" begin="3" end="${totalPlaces}" step="4">
                                                                    <div class="seat-row">
                                                                        <c:forEach var="i" begin="${start}" end="${start + 3 <= totalPlaces ? start + 3 : totalPlaces}">
                                                                            <label class="seat">
                                                                                <input type="checkbox"
                                                                                       class="seat-checkbox"
                                                                                       name="places_${t.id}"
                                                                                       value="${i}">
                                                                                <span>${i}</span>
                                                                            </label>
                                                                        </c:forEach>
                                                                    </div>
                                                                </c:forEach>
                                                            </c:if>

                                                        </div>
                                                    </div>
                                                </div>
                                            </c:forEach>

                                        </div>

                                        <!-- Actions -->
                                        <div class="form-actions text-right">
                                            <a href="${pageContext.request.contextPath}/voyages"
                                               class="btn btn-warning mr-1">
                                                <i class="ft-x"></i> Annuler
                                            </a>
                                            <button type="submit" class="btn btn-primary">
                                                <i class="la la-check-square-o"></i> Enregistrer
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
