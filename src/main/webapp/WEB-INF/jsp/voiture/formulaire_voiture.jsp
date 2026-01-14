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
                <h3 class="content-header-title">Formulaire - Creer un Voiture</h3>
            </div>
            <div class="content-header-right col-md-6 col-12 mb-2 text-right">
                <a href="/voitures" class="btn btn-secondary">
                    <i class="la la-list"></i> Liste des voitures
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

                                    <!-- FORMULAIRE -->
                                    <form class="form form-horizontal form-bordered"
                                          method="post"
                                          action="${pageContext.request.contextPath}/voitures/ajouter">

                                        <div class="form-body">

                                            

                                            <!-- Clients -->
                                            <div class="form-group row">
                                                <label class="col-md-3 label-control">
                                                    Immatricule
                                                </label>
                                                <div class="col-md-9">
                                                    <input type="text"
                                                           name="immatricule"
                                                           class="form-control"
                                                           placeholder="Ex: 9090 TBR ..."
                                                           required>
                                                </div>
                                            </div>

                                            
                                            <div class="form-group row">
                                                <label class="col-md-3 label-control">
                                                    Nombre de place
                                                </label>
                                                <div class="col-md-9">
                                                    <input type="number"
                                                           name="nbPlace"
                                                           class="form-control"
                                                           placeholder="Capacite de voiture ..."
                                                           required>
                                                </div>
                                            </div>

                                            

                                        <!-- Actions -->
                                        <div class="form-actions text-right">
                                            <a href="${pageContext.request.contextPath}/voitures"
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
