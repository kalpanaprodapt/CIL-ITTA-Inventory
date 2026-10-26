import { http } from '@/shared/api/http';
import type { LifecycleStatus, Page } from '@/shared/types';
import { FORECASTMODEL_PATH } from './types';
import type { ForecastModel, ForecastModelFilter, ForecastModelInput, ForecastModelSummary, ForecastModelUpdateInput } from './types';
import { toForecastModelQuery } from './filterState';

export interface BulkResult<T> {
  succeeded: T[];
  failures: { index: number; message: string }[];
}

export function searchForecastModels(
  filter: ForecastModelFilter,
  page = 0,
  size = 20,
  sort?: string,
): Promise<Page<ForecastModel>> {
  return http.get<Page<ForecastModel>>(FORECASTMODEL_PATH, { ...toForecastModelQuery(filter), page, size, sort });
}

export function getForecastModel(id: string): Promise<ForecastModel> {
  return http.get<ForecastModel>(`${FORECASTMODEL_PATH}/${id}`);
}

export function getForecastModelByCode(code: string): Promise<ForecastModel> {
  return http.get<ForecastModel>(`${FORECASTMODEL_PATH}/by-code/${encodeURIComponent(code)}`);
}

export function listForecastModelSummaries(status?: LifecycleStatus): Promise<ForecastModelSummary[]> {
  return http.get<ForecastModelSummary[]>(`${FORECASTMODEL_PATH}/summaries`, { status });
}

export function createForecastModel(input: ForecastModelInput): Promise<ForecastModel> {
  return http.post<ForecastModel>(FORECASTMODEL_PATH, input);
}

export function updateForecastModel(id: string, input: ForecastModelUpdateInput): Promise<ForecastModel> {
  return http.put<ForecastModel>(`${FORECASTMODEL_PATH}/${id}`, input);
}

export function changeForecastModelStatus(id: string, status: LifecycleStatus): Promise<ForecastModel> {
  return http.patch<ForecastModel>(`${FORECASTMODEL_PATH}/${id}/status`, undefined, { status });
}

export function deleteForecastModel(id: string): Promise<void> {
  return http.delete(`${FORECASTMODEL_PATH}/${id}`);
}

export function bulkCreateForecastModels(inputs: ForecastModelInput[]): Promise<BulkResult<ForecastModel>> {
  return http.post<BulkResult<ForecastModel>>(`${FORECASTMODEL_PATH}/bulk`, inputs);
}

export function getForecastModelStatusStats(): Promise<Record<LifecycleStatus, number>> {
  return http.get<Record<LifecycleStatus, number>>(`${FORECASTMODEL_PATH}/stats/status`);
}

export function exportForecastModelsCsv(filter: ForecastModelFilter): Promise<Blob> {
  return http.blob(`${FORECASTMODEL_PATH}/export`, toForecastModelQuery(filter));
}
