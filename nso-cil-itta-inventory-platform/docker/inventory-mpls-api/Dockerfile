# syntax=docker/dockerfile:1.7
# inventory-mpls-api - FastAPI facade for the MPLS and VPN domain
FROM python:3.12-slim AS build
ENV PIP_DISABLE_PIP_VERSION_CHECK=1 PIP_NO_CACHE_DIR=1
WORKDIR /src
COPY pyproject.toml README.md ./
COPY src ./src
RUN pip wheel --wheel-dir /wheels .

FROM python:3.12-slim AS runtime
RUN groupadd --system --gid 10001 app && useradd --system --uid 10001 --gid app app
WORKDIR /app
COPY --from=build /wheels /wheels
RUN pip install --no-index --find-links=/wheels nso-cil-itta-inventory-api && rm -rf /wheels
USER 10001:10001
EXPOSE 8000
ENV PYTHONUNBUFFERED=1 PYTHONDONTWRITEBYTECODE=1
HEALTHCHECK --interval=30s --timeout=3s --start-period=10s --retries=3 \
  CMD python -c "import urllib.request as u; u.urlopen('http://localhost:8000/healthz', timeout=2)" || exit 1
CMD ["uvicorn", "itta_api.domains.mpls.app:app", "--host", "0.0.0.0", "--port", "8000", "--proxy-headers"]
