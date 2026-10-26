{{- define "itta.name" -}}
{{- default .Chart.Name .Values.nameOverride | trunc 63 | trimSuffix "-" -}}
{{- end -}}

{{- define "itta.labels" -}}
app.kubernetes.io/name: {{ include "itta.name" . }}
app.kubernetes.io/instance: {{ include "itta.name" . }}-{{ .Values.environment }}
app.kubernetes.io/version: {{ .Chart.AppVersion | quote }}
app.kubernetes.io/part-of: itta-inventory
app.kubernetes.io/managed-by: argocd
cil.uniti.com/domain: {{ .Values.domain }}
cil.uniti.com/environment: {{ .Values.environment }}
{{- end -}}

{{- define "itta.selector" -}}
app.kubernetes.io/name: {{ include "itta.name" . }}
app.kubernetes.io/instance: {{ include "itta.name" . }}-{{ .Values.environment }}
{{- end -}}
