import { DateRange, FilterField, Input, MultiSelect, RangeInputs, TriState } from '@/shared/components/filters';
import { LIFECYCLE_OPTIONS } from '@/shared/types';
import { VRF_INSTANCE_LIFECYCLE_STAGE_OPTIONS } from './types';
import type { VrfInstanceFilter } from './types';

interface Props {
  value: VrfInstanceFilter;
  onChange: (next: VrfInstanceFilter) => void;
  onReset: () => void;
}

export function VrfInstanceFilterBar({ value, onChange, onReset }: Props) {
  const patch = (partial: VrfInstanceFilter) => onChange({ ...value, ...partial });

  return (
    <form className="filter-bar" aria-label="Vrf Instance filters" onSubmit={(ev) => ev.preventDefault()}>
      <FilterField label="Search">
        <Input
          aria-label="Search Vrf Instance"
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
      <FilterField label="Vrf Name">
        <Input
          aria-label="Vrf Name filter"
          value={value.vrfName ?? ''}
          onChange={(v) => patch({ vrfName: v || undefined })}
        />
      </FilterField>
      <FilterField label="Route Distinguisher">
        <Input
          aria-label="Route Distinguisher filter"
          value={value.routeDistinguisher ?? ''}
          onChange={(v) => patch({ routeDistinguisher: v || undefined })}
        />
      </FilterField>
      <FilterField label="Import Targets">
        <Input
          aria-label="Import Targets filter"
          value={value.importTargets ?? ''}
          onChange={(v) => patch({ importTargets: v || undefined })}
        />
      </FilterField>
      <FilterField label="Export Targets">
        <Input
          aria-label="Export Targets filter"
          value={value.exportTargets ?? ''}
          onChange={(v) => patch({ exportTargets: v || undefined })}
        />
      </FilterField>
      <FilterField label="Device Id">
        <Input
          aria-label="Device Id filter"
          placeholder="UUID"
          value={value.deviceId ?? ''}
          onChange={(v) => patch({ deviceId: v || undefined })}
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
          options={VRF_INSTANCE_LIFECYCLE_STAGE_OPTIONS}
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
      <div className="filter-bar__actions">
        <button type="button" className="btn btn--ghost" onClick={onReset}>
          Reset filters
        </button>
      </div>
    </form>
  );
}
