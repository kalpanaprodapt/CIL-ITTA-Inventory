import type { ForecastModel, ForecastModelInput } from '../types';

export const forecastModelInput: ForecastModelInput = {
  description: 'description-1',
  externalRef: 'externalRef-1',
  modelName: 'modelName-1',
  algorithm: 'LINEAR',
  trainedOn: '2026-02-11T10:15:30Z',
  mape: 13.5,
  criticality: 'LOW',
  costCenter: 'costCenter-1',
  notes: 'notes-1',
  effectiveFrom: '2026-02-11',
  effectiveTo: '2026-02-11',
  code: 'FORECA-0001',
  name: 'Forecast Model one',
  status: 'ACTIVE',
};

export const forecastModelFixture: ForecastModel = {
  ...forecastModelInput,
  id: '11111111-1111-4111-8111-111111111111',
  tenantId: 'tenant-a',
  version: 0,
  createdAt: '2026-01-01T00:00:00Z',
  updatedAt: '2026-01-02T00:00:00Z',
  createdBy: 'tester',
  updatedBy: 'tester',
};

export const forecastModelPage = {
  content: [forecastModelFixture],
  page: 0,
  size: 20,
  totalElements: 1,
  totalPages: 1,
};
