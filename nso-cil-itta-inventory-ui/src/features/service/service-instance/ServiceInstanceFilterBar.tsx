import { DateRange, FilterField, Input, MultiSelect, RangeInputs, TriState } from '@/shared/components/filters';
import { LIFECYCLE_OPTIONS } from '@/shared/types';
import { SERVICE_INSTANCE_SERVICE_STATE_OPTIONS, SERVICE_INSTANCE_LIFECYCLE_STAGE_OPTIONS, SERVICE_INSTANCE_CRITICALITY_OPTIONS } from './types';
import type { ServiceInstanceFilter } from './types';

interface Props {
  value: ServiceInstanceFilter;
  onChange: (next: ServiceInstanceFilter) => void;
  onReset: () => void;
}

export function ServiceInstanceFilterBar({ value, onChange, onReset }: Props) {
  const patch = (partial: ServiceInstanceFilter) => onChange({ ...value, ...partial });

  return (
    <form className="filter-bar" aria-label="Service Instance filters" onSubmit={(ev) => ev.preventDefault()}>
      <FilterField label="Search">
        <Input
          aria-label="Search Service Instance"
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
      <FilterField label="Catalog Item Id">
        <Input
          aria-label="Catalog Item Id filter"
          placeholder="UUID"
          value={value.catalogItemId ?? ''}
          onChange={(v) => patch({ catalogItemId: v || undefined })}
        />
      </FilterField>
      <FilterField label="Customer Id">
        <Input
          aria-label="Customer Id filter"
          placeholder="UUID"
          value={value.customerId ?? ''}
          onChange={(v) => patch({ customerId: v || undefined })}
        />
      </FilterField>
      <FilterField label="Activated On">
        <DateRange
          ariaLabel="Activated On"
          from={value.activatedOnFrom}
          to={value.activatedOnTo}
          onChange={(from, to) => patch({ activatedOnFrom: from, activatedOnTo: to })}
        />
      </FilterField>
      <FilterField label="Bandwidth Mbps">
        <RangeInputs
          ariaLabel="Bandwidth Mbps"
          min={value.bandwidthMbpsMin}
          max={value.bandwidthMbpsMax}
          onChange={(min, max) => patch({ bandwidthMbpsMin: min, bandwidthMbpsMax: max })}
        />
      </FilterField>
      <FilterField label="Service State">
        <MultiSelect
          ariaLabel="Service State filter"
          options={SERVICE_INSTANCE_SERVICE_STATE_OPTIONS}
          value={value.serviceState ?? []}
          onChange={(v) => patch({ serviceState: v.length ? v : undefined })}
        />
      </FilterField>
      <FilterField label="Lifecycle Stage">
        <MultiSelect
          ariaLabel="Lifecycle Stage filter"
          options={SERVICE_INSTANCE_LIFECYCLE_STAGE_OPTIONS}
          value={value.lifecycleStage ?? []}
          onChange={(v) => patch({ lifecycleStage: v.length ? v : undefined })}
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
          options={SERVICE_INSTANCE_CRITICALITY_OPTIONS}
          value={value.criticality ?? []}
          onChange={(v) => patch({ criticality: v.length ? v : undefined })}
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
