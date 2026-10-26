import type { LifecycleStatus, SortState } from '@/shared/types';

export const FORECAST_MODEL_ALGORITHM_VALUES = ['LINEAR', 'HOLT_WINTERS', 'ARIMA', 'PROPHET'] as const;
export type ForecastModelAlgorithm = (typeof FORECAST_MODEL_ALGORITHM_VALUES)[number];
export const FORECAST_MODEL_ALGORITHM_LABELS: Record<ForecastModelAlgorithm, string> = {
  LINEAR: 'Linear',
  HOLT_WINTERS: 'Holt Winters',
  ARIMA: 'Arima',
  PROPHET: 'Prophet',
};
export const FORECAST_MODEL_ALGORITHM_OPTIONS = FORECAST_MODEL_ALGORITHM_VALUES.map((value) => ({ value, label: FORECAST_MODEL_ALGORITHM_LABELS[value] }));

export const FORECAST_MODEL_CRITICALITY_VALUES = ['LOW', 'MEDIUM', 'HIGH', 'CRITICAL'] as const;
export type ForecastModelCriticality = (typeof FORECAST_MODEL_CRITICALITY_VALUES)[number];
export const FORECAST_MODEL_CRITICALITY_LABELS: Record<ForecastModelCriticality, string> = {
  LOW: 'Low',
  MEDIUM: 'Medium',
  HIGH: 'High',
  CRITICAL: 'Critical',
};
export const FORECAST_MODEL_CRITICALITY_OPTIONS = FORECAST_MODEL_CRITICALITY_VALUES.map((value) => ({ value, label: FORECAST_MODEL_CRITICALITY_LABELS[value] }));

export interface ForecastModel {
  id: string;
  tenantId: string;
  version: number;
  createdAt?: string;
  updatedAt?: string;
  createdBy?: string;
  updatedBy?: string;
  code: string;
  name: string;
  description?: string;
  status: LifecycleStatus;
  externalRef?: string;
  modelName: string;
  algorithm?: ForecastModelAlgorithm;
  trainedOn?: string;
  mape?: number;
  criticality?: ForecastModelCriticality;
  costCenter?: string;
  notes?: string;
  effectiveFrom?: string;
  effectiveTo?: string;
}

export interface ForecastModelInput {
  code: string;
  name: string;
  description?: string;
  status: LifecycleStatus;
  externalRef?: string;
  modelName: string;
  algorithm?: ForecastModelAlgorithm;
  trainedOn?: string;
  mape?: number;
  criticality?: ForecastModelCriticality;
  costCenter?: string;
  notes?: string;
  effectiveFrom?: string;
  effectiveTo?: string;
}

export type ForecastModelFormValues = Partial<ForecastModelInput>;

export interface ForecastModelUpdateInput extends Partial<ForecastModelInput> {
  expectedVersion?: number;
}

export interface ForecastModelFilter {
  q?: string;
  status?: LifecycleStatus[];
  code?: string;
  name?: string;
  externalRef?: string;
  modelName?: string;
  algorithm?: ForecastModelAlgorithm[];
  trainedOnFrom?: string;
  trainedOnTo?: string;
  mapeMin?: number;
  mapeMax?: number;
  criticality?: ForecastModelCriticality[];
  costCenter?: string;
  effectiveFromFrom?: string;
  effectiveFromTo?: string;
  effectiveToFrom?: string;
  effectiveToTo?: string;
}

export type ForecastModelSort = SortState<keyof ForecastModel & string>;

export interface ForecastModelSummary {
  id: string;
  code: string;
  name: string;
  status: LifecycleStatus;
}

export const FORECASTMODEL_PATH = '/api/v1/forecast-models';
export const FORECASTMODEL_TITLE = 'Forecast Model';
export const FORECASTMODEL_TITLE_PLURAL = 'Forecast Models';

export const FORECASTMODEL_DEFAULTS: ForecastModelFormValues = {
  code: '',
  name: '',
  status: 'PLANNED',
  modelName: '',
};
