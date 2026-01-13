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
                <h3 class="content-header-title">Ajouter un détail de voyage</h3>
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

                                    <c:if test="${not empty errorMessage}">
                                        <div class="alert alert-danger">
                                            ${errorMessage}
                                        </div>
                                    </c:if>

                                    <form class="form form-horizontal form-bordered"
                                          method="post"
                                          action="${pageContext.request.contextPath}/voyages/${idVoyage}/details/ajouter">

                                        <div class="form-body">

                                            <!-- Voiture -->
                                            <div class="form-group row">
                                                <label class="col-md-3 label-control">Voiture</label>
                                                <div class="col-md-9">
                                                    <select name="id_voiture" class="form-control" required>
                                                        <option value="">-- Choisir une voiture --</option>
                                                        <c:forEach var="v" items="${liste_voiture}">
                                                            <option value="${v.id}">
                                                                ${v.immatricule} - ${v.nbPlace} places
                                                            </option>
                                                        </c:forEach>
                                                    </select>
                                                </div>
                                            </div>

                                            <!-- Type de voyage -->
                                            <div class="form-group row">
                                                <label class="col-md-3 label-control">Type de voyage</label>
                                                <div class="col-md-9">
                                                    <select name="id_type_voyage" class="form-control" required>
                                                        <option value="">-- Choisir un type --</option>
                                                        <c:forEach var="t" items="${liste_type_voyage}">
                                                            <option value="${t.id}">
                                                                ${t.description}
                                                            </option>
                                                        </c:forEach>
                                                    </select>
                                                </div>
                                            </div>

                                            <!-- Heure de départ -->
                                            <div class="form-group row">
                                                <label class="col-md-3 label-control">Heure de départ</label>
                                                <div class="col-md-9">
                                                    <input type="time"
                                                           name="heure_depart"
                                                           class="form-control"
                                                           required>
                                                </div>
                                            </div>

                                        </div>

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
