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
                <h3 class="content-header-title">Ajouter un detail de voyage</h3>
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

                                            <!-- Heure de depart -->
                                            <div class="form-group row">
                                                <label class="col-md-3 label-control">Heure de depart</label>
                                                <div class="col-md-9">
                                                    <input type="time"
                                                           name="heure_depart"
                                                           class="form-control"
                                                           required>
                                                </div>
                                            </div>

                                            <!-- Diffusion publications -->
                                            <div class="form-group row">
                                                <label class="col-md-3 label-control">Publications</label>
                                                <div class="col-md-9">
                                                    <div class="custom-control custom-checkbox mb-1">
                                                        <input type="checkbox" class="custom-control-input" id="toggleDiffusion">
                                                        <label class="custom-control-label" for="toggleDiffusion">Diffuser des publications pour ce voyage</label>
                                                    </div>

                                                    <div id="diffusionPanel" style="display:none; border: 1px solid #e5e5e5; padding: 12px; border-radius: 4px;">
                                                        <div class="d-flex justify-content-between align-items-center mb-1">
                                                            <strong>Diffusions</strong>
                                                            <button type="button" class="btn btn-sm btn-outline-primary" id="addDiffusionBtn">
                                                                <i class="la la-plus"></i> Ajouter
                                                            </button>
                                                        </div>

                                                        <div id="diffusionRows"></div>
                                                        <small class="text-muted">Laissez vide ou 0 pour ignorer une ligne.</small>

                                                        <template id="diffusionRowTemplate">
                                                            <div class="form-group row mb-1">
                                                                <div class="col-md-7">
                                                                    <select name="id_publication" class="form-control">
                                                                        <option value="">-- Choisir une publication --</option>
                                                                        <c:forEach var="p" items="${liste_publication}">
                                                                            <option value="${p.id}">${p.titre}</option>
                                                                        </c:forEach>
                                                                    </select>
                                                                </div>
                                                                <div class="col-md-3">
                                                                    <input type="number" min="0" name="nb_repetition" class="form-control" placeholder="Repetition" />
                                                                </div>
                                                                <div class="col-md-2 text-right">
                                                                    <button type="button" class="btn btn-sm btn-outline-danger btn-remove-diffusion">
                                                                        <i class="la la-trash"></i>
                                                                    </button>
                                                                </div>
                                                            </div>
                                                        </template>
                                                    </div>
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

<script type="text/javascript">
    (function () {
        var toggle = document.getElementById('toggleDiffusion');
        var panel = document.getElementById('diffusionPanel');
        var rows = document.getElementById('diffusionRows');
        var addBtn = document.getElementById('addDiffusionBtn');
        var tpl = document.getElementById('diffusionRowTemplate');

        if (!toggle || !panel || !rows || !addBtn || !tpl) {
            return;
        }

        function setEnabled(enabled) {
            var inputs = panel.querySelectorAll('select, input');
            inputs.forEach(function (el) {
                el.disabled = !enabled;
            });
        }

        function wireRemoveButtons() {
            var buttons = rows.querySelectorAll('.btn-remove-diffusion');
            buttons.forEach(function (btn) {
                if (btn.dataset.bound === '1') return;
                btn.dataset.bound = '1';
                btn.addEventListener('click', function () {
                    var row = btn.closest('.form-group');
                    if (row) {
                        rows.removeChild(row);
                    }
                    if (rows.children.length === 0) {
                        createRow();
                    }
                });
            });
        }

        function createRow() {
            var fragment = tpl.content.cloneNode(true);
            rows.appendChild(fragment);
            wireRemoveButtons();
        }

        toggle.addEventListener('change', function () {
            panel.style.display = toggle.checked ? '' : 'none';
            setEnabled(toggle.checked);
            if (toggle.checked && rows.children.length === 0) createRow();
        });

        addBtn.addEventListener('click', function () {
            createRow();
        });

        // init
        setEnabled(false);
    })();
</script>
</body>
</html>
