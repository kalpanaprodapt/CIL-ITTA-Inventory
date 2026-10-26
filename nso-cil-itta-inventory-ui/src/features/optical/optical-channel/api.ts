import { http } from '@/shared/api/http';
import type { LifecycleStatus, Page } from '@/shared/types';
import { OPTICALCHANNEL_PATH } from './types';
import type { OpticalChannel, OpticalChannelFilter, OpticalChannelInput, OpticalChannelSummary, OpticalChannelUpdateInput } from './types';
import { toOpticalChannelQuery } from './filterState';

export interface BulkResult<T> {
  succeeded: T[];
  failures: { index: number; message: string }[];
}

export function searchOpticalChannels(
  filter: OpticalChannelFilter,
  page = 0,
  size = 20,
  sort?: string,
): Promise<Page<OpticalChannel>> {
  return http.get<Page<OpticalChannel>>(OPTICALCHANNEL_PATH, { ...toOpticalChannelQuery(filter), page, size, sort });
}

export function getOpticalChannel(id: string): Promise<OpticalChannel> {
  return http.get<OpticalChannel>(`${OPTICALCHANNEL_PATH}/${id}`);
}

export function getOpticalChannelByCode(code: string): Promise<OpticalChannel> {
  return http.get<OpticalChannel>(`${OPTICALCHANNEL_PATH}/by-code/${encodeURIComponent(code)}`);
}

export function listOpticalChannelSummaries(status?: LifecycleStatus): Promise<OpticalChannelSummary[]> {
  return http.get<OpticalChannelSummary[]>(`${OPTICALCHANNEL_PATH}/summaries`, { status });
}

export function createOpticalChannel(input: OpticalChannelInput): Promise<OpticalChannel> {
  return http.post<OpticalChannel>(OPTICALCHANNEL_PATH, input);
}

export function updateOpticalChannel(id: string, input: OpticalChannelUpdateInput): Promise<OpticalChannel> {
  return http.put<OpticalChannel>(`${OPTICALCHANNEL_PATH}/${id}`, input);
}

export function changeOpticalChannelStatus(id: string, status: LifecycleStatus): Promise<OpticalChannel> {
  return http.patch<OpticalChannel>(`${OPTICALCHANNEL_PATH}/${id}/status`, undefined, { status });
}

export function deleteOpticalChannel(id: string): Promise<void> {
  return http.delete(`${OPTICALCHANNEL_PATH}/${id}`);
}

export function bulkCreateOpticalChannels(inputs: OpticalChannelInput[]): Promise<BulkResult<OpticalChannel>> {
  return http.post<BulkResult<OpticalChannel>>(`${OPTICALCHANNEL_PATH}/bulk`, inputs);
}

export function getOpticalChannelStatusStats(): Promise<Record<LifecycleStatus, number>> {
  return http.get<Record<LifecycleStatus, number>>(`${OPTICALCHANNEL_PATH}/stats/status`);
}

export function exportOpticalChannelsCsv(filter: OpticalChannelFilter): Promise<Blob> {
  return http.blob(`${OPTICALCHANNEL_PATH}/export`, toOpticalChannelQuery(filter));
}

export function listOpticalChannelBySystemId(systemId: string, page = 0, size = 20): Promise<Page<OpticalChannel>> {
  return http.get<Page<OpticalChannel>>(`${OPTICALCHANNEL_PATH}/by-system-id/${systemId}`, { page, size });
}
