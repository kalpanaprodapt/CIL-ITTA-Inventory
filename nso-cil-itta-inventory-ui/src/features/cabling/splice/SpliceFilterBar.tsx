import { DateRange, FilterField, Input, MultiSelect, RangeInputs, TriState } from '@/shared/components/filters';
import { LIFECYCLE_OPTIONS } from '@/shared/types';
import { SPLICE_LIFECYCLE_STAGE_OPTIONS, SPLICE_CRITICALITY_OPTIONS } from './types';
import type { SpliceFilter } from './types';

interface Props {
  value: SpliceFilter;
  onChange: (next: SpliceFilter) => void;
  onReset: () => void;
}

export function SpliceFilterBar({ value, onChange, onReset }: Props) {
  const patch = (partial: SpliceFilter) => onChange({ ...value, ...partial });

  return (
    <form className="filter-bar" aria-label="Splice filters" onSubmit={(ev) => ev.preventDefault()}>
      <FilterField label="Search">
        <Input
          aria-label="Search Splice"
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
      <FilterField label="Enclosure Id">
        <Input
          aria-label="Enclosure Id filter"
          value={value.enclosureId ?? ''}
          onChange={(v) => patch({ enclosureId: v || undefined })}
        />
      </FilterField>
      <FilterField label="A Strand Id">
        <Input
          aria-label="A Strand Id filter"
          placeholder="UUID"
          value={value.aStrandId ?? ''}
          onChange={(v) => patch({ aStrandId: v || undefined })}
        />
      </FilterField>
      <FilterField label="Z Strand Id">
        <Input
          aria-label="Z Strand Id filter"
          placeholder="UUID"
          value={value.zStrandId ?? ''}
          onChange={(v) => patch({ zStrandId: v || undefined })}
        />
      </FilterField>
      <FilterField label="Loss Db">
        <RangeInputs
          ariaLabel="Loss Db"
          min={value.lossDbMin}
          max={value.lossDbMax}
          onChange={(min, max) => patch({ lossDbMin: min, lossDbMax: max })}
        />
      </FilterField>
      <FilterField label="Spliced On">
        <DateRange
          ariaLabel="Spliced On"
          from={value.splicedOnFrom}
          to={value.splicedOnTo}
          onChange={(from, to) => patch({ splicedOnFrom: from, splicedOnTo: to })}
        />
      </FilterField>
      <FilterField label="Lifecycle Stage">
        <MultiSelect
          ariaLabel="Lifecycle Stage filter"
          options={SPLICE_LIFECYCLE_STAGE_OPTIONS}
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
          options={SPLICE_CRITICALITY_OPTIONS}
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
