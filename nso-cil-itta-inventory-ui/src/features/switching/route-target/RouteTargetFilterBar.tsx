import { DateRange, FilterField, Input, MultiSelect, RangeInputs, TriState } from '@/shared/components/filters';
import { LIFECYCLE_OPTIONS } from '@/shared/types';
import { ROUTE_TARGET_RT_TYPE_OPTIONS, ROUTE_TARGET_LIFECYCLE_STAGE_OPTIONS, ROUTE_TARGET_CRITICALITY_OPTIONS } from './types';
import type { RouteTargetFilter } from './types';

interface Props {
  value: RouteTargetFilter;
  onChange: (next: RouteTargetFilter) => void;
  onReset: () => void;
}

export function RouteTargetFilterBar({ value, onChange, onReset }: Props) {
  const patch = (partial: RouteTargetFilter) => onChange({ ...value, ...partial });

  return (
    <form className="filter-bar" aria-label="Route Target filters" onSubmit={(ev) => ev.preventDefault()}>
      <FilterField label="Search">
        <Input
          aria-label="Search Route Target"
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
      <FilterField label="Asn">
        <RangeInputs
          ariaLabel="Asn"
          min={value.asnMin}
          max={value.asnMax}
          onChange={(min, max) => patch({ asnMin: min, asnMax: max })}
        />
      </FilterField>
      <FilterField label="Assigned Number">
        <RangeInputs
          ariaLabel="Assigned Number"
          min={value.assignedNumberMin}
          max={value.assignedNumberMax}
          onChange={(min, max) => patch({ assignedNumberMin: min, assignedNumberMax: max })}
        />
      </FilterField>
      <FilterField label="Rt Type">
        <MultiSelect
          ariaLabel="Rt Type filter"
          options={ROUTE_TARGET_RT_TYPE_OPTIONS}
          value={value.rtType ?? []}
          onChange={(v) => patch({ rtType: v.length ? v : undefined })}
        />
      </FilterField>
      <FilterField label="Vrf Id">
        <Input
          aria-label="Vrf Id filter"
          placeholder="UUID"
          value={value.vrfId ?? ''}
          onChange={(v) => patch({ vrfId: v || undefined })}
        />
      </FilterField>
      <FilterField label="Lifecycle Stage">
        <MultiSelect
          ariaLabel="Lifecycle Stage filter"
          options={ROUTE_TARGET_LIFECYCLE_STAGE_OPTIONS}
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
          options={ROUTE_TARGET_CRITICALITY_OPTIONS}
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
