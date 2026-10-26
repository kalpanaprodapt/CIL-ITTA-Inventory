import { DateRange, FilterField, Input, MultiSelect, RangeInputs, TriState } from '@/shared/components/filters';
import { LIFECYCLE_OPTIONS } from '@/shared/types';
import { INTEGRATION_ENDPOINT_AUTH_TYPE_OPTIONS, INTEGRATION_ENDPOINT_CRITICALITY_OPTIONS } from './types';
import type { IntegrationEndpointFilter } from './types';

interface Props {
  value: IntegrationEndpointFilter;
  onChange: (next: IntegrationEndpointFilter) => void;
  onReset: () => void;
}

export function IntegrationEndpointFilterBar({ value, onChange, onReset }: Props) {
  const patch = (partial: IntegrationEndpointFilter) => onChange({ ...value, ...partial });

  return (
    <form className="filter-bar" aria-label="Integration Endpoint filters" onSubmit={(ev) => ev.preventDefault()}>
      <FilterField label="Search">
        <Input
          aria-label="Search Integration Endpoint"
          placeholder="code, name or description"
          value={value.q ?? ''}
          onChange={(v) => patch({ q: v || undefined })}
        />
      </FilterField>
      <FilterField label="Status">
        <MultiSelect
          ariaLabel="Status filter"
          options={LIFECYCLE_OPTIONS}
          value={value.status ?? []}
          onChange={(v) => patch({ status: v.length ? v : undefined })}
        />
      </FilterField>
      <FilterField label="Code">
        <Input
          aria-label="Code filter"
          value={value.code ?? ''}
          onChange={(v) => patch({ code: v || undefined })}
        />
      </FilterField>
      <FilterField label="Name">
        <Input
          aria-label="Name filter"
          value={value.name ?? ''}
          onChange={(v) => patch({ name: v || undefined })}
        />
      </FilterField>
      <FilterField label="External Ref">
        <Input
          aria-label="External Ref filter"
          value={value.externalRef ?? ''}
          onChange={(v) => patch({ externalRef: v || undefined })}
        />
      </FilterField>
      <FilterField label="Endpoint Name">
        <Input
          aria-label="Endpoint Name filter"
          value={value.endpointName ?? ''}
          onChange={(v) => patch({ endpointName: v || undefined })}
        />
      </FilterField>
      <FilterField label="Target System">
        <Input
          aria-label="Target System filter"
          value={value.targetSystem ?? ''}
          onChange={(v) => patch({ targetSystem: v || undefined })}
        />
      </FilterField>
      <FilterField label="Base Url">
        <Input
          aria-label="Base Url filter"
          value={value.baseUrl ?? ''}
          onChange={(v) => patch({ baseUrl: v || undefined })}
        />
      </FilterField>
      <FilterField label="Auth Type">
        <MultiSelect
          ariaLabel="Auth Type filter"
          options={INTEGRATION_ENDPOINT_AUTH_TYPE_OPTIONS}
          value={value.authType ?? []}
          onChange={(v) => patch({ authType: v.length ? v : undefined })}
        />
      </FilterField>
      <FilterField label="Timeout Ms">
        <RangeInputs
          ariaLabel="Timeout Ms"
          min={value.timeoutMsMin}
          max={value.timeoutMsMax}
          onChange={(min, max) => patch({ timeoutMsMin: min, timeoutMsMax: max })}
        />
      </FilterField>
      <FilterField label="Source System">
        <Input
          aria-label="Source System filter"
          value={value.sourceSystem ?? ''}
          onChange={(v) => patch({ sourceSystem: v || undefined })}
        />
      </FilterField>
      <FilterField label="Region">
        <Input
          aria-label="Region filter"
          value={value.region ?? ''}
          onChange={(v) => patch({ region: v || undefined })}
        />
      </FilterField>
      <FilterField label="Owner">
        <Input
          aria-label="Owner filter"
          value={value.owner ?? ''}
          onChange={(v) => patch({ owner: v || undefined })}
        />
      </FilterField>
      <FilterField label="Criticality">
        <MultiSelect
          ariaLabel="Criticality filter"
          options={INTEGRATION_ENDPOINT_CRITICALITY_OPTIONS}
          value={value.criticality ?? []}
          onChange={(v) => patch({ criticality: v.length ? v : undefined })}
        />
      </FilterField>
      <FilterField label="Cost Center">
        <Input
          aria-label="Cost Center filter"
          value={value.costCenter ?? ''}
          onChange={(v) => patch({ costCenter: v || undefined })}
        />
      </FilterField>
      <div className="filter-bar__actions">
        <button type="button" className="btn btn--ghost" onClick={onReset}>
          Reset filters
        </button>
      </div>
    </form>
  );
}
