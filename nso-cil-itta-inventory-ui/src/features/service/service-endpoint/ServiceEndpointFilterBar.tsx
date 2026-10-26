import { DateRange, FilterField, Input, MultiSelect, RangeInputs, TriState } from '@/shared/components/filters';
import { LIFECYCLE_OPTIONS } from '@/shared/types';
import { SERVICE_ENDPOINT_ENDPOINT_ROLE_OPTIONS, SERVICE_ENDPOINT_LIFECYCLE_STAGE_OPTIONS } from './types';
import type { ServiceEndpointFilter } from './types';

interface Props {
  value: ServiceEndpointFilter;
  onChange: (next: ServiceEndpointFilter) => void;
  onReset: () => void;
}

export function ServiceEndpointFilterBar({ value, onChange, onReset }: Props) {
  const patch = (partial: ServiceEndpointFilter) => onChange({ ...value, ...partial });

  return (
    <form className="filter-bar" aria-label="Service Endpoint filters" onSubmit={(ev) => ev.preventDefault()}>
      <FilterField label="Search">
        <Input
          aria-label="Search Service Endpoint"
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
      <FilterField label="Service Id">
        <Input
          aria-label="Service Id filter"
          placeholder="UUID"
          value={value.serviceId ?? ''}
          onChange={(v) => patch({ serviceId: v || undefined })}
        />
      </FilterField>
      <FilterField label="Site Id">
        <Input
          aria-label="Site Id filter"
          placeholder="UUID"
          value={value.siteId ?? ''}
          onChange={(v) => patch({ siteId: v || undefined })}
        />
      </FilterField>
      <FilterField label="Port Id">
        <Input
          aria-label="Port Id filter"
          placeholder="UUID"
          value={value.portId ?? ''}
          onChange={(v) => patch({ portId: v || undefined })}
        />
      </FilterField>
      <FilterField label="Endpoint Role">
        <MultiSelect
          ariaLabel="Endpoint Role filter"
          options={SERVICE_ENDPOINT_ENDPOINT_ROLE_OPTIONS}
          value={value.endpointRole ?? []}
          onChange={(v) => patch({ endpointRole: v.length ? v : undefined })}
        />
      </FilterField>
      <FilterField label="Handoff">
        <Input
          aria-label="Handoff filter"
          value={value.handoff ?? ''}
          onChange={(v) => patch({ handoff: v || undefined })}
        />
      </FilterField>
      <FilterField label="Effective From">
        <DateRange
          ariaLabel="Effective From"
          from={value.effectiveFromFrom}
          to={value.effectiveFromTo}
          onChange={(from, to) => patch({ effectiveFromFrom: from, effectiveFromTo: to })}
        />
      </FilterField>
      <FilterField label="Effective To">
        <DateRange
          ariaLabel="Effective To"
          from={value.effectiveToFrom}
          to={value.effectiveToTo}
          onChange={(from, to) => patch({ effectiveToFrom: from, effectiveToTo: to })}
        />
      </FilterField>
      <FilterField label="Labels">
        <Input
          aria-label="Labels filter"
          value={value.labels ?? ''}
          onChange={(v) => patch({ labels: v || undefined })}
        />
      </FilterField>
      <FilterField label="Lifecycle Stage">
        <MultiSelect
          ariaLabel="Lifecycle Stage filter"
          options={SERVICE_ENDPOINT_LIFECYCLE_STAGE_OPTIONS}
          value={value.lifecycleStage ?? []}
          onChange={(v) => patch({ lifecycleStage: v.length ? v : undefined })}
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
